#!/usr/bin/env python
#
# This is a test script which tests several aspects of the Python SML interface
#  including kernel and agent creation, running, registering and unregistering
#  several kinds of callbacks, reinitializing, agent destruction, and kernel
#  destruction (and maybe some other things, too).
#
import atexit
from pathlib import Path
import sys
import time

import Python_sml_ClientInterface
from Python_sml_ClientInterface import *

BASEDIR = Path(Python_sml_ClientInterface.__file__).parent.parent


towers_of_hanoi_file = BASEDIR / 'share/soar/Demos/towers-of-hanoi/towers-of-hanoi.soar'
toh_test_file = BASEDIR / 'share/soar/Tests/TOHtest.soar'
for source_file in (towers_of_hanoi_file, toh_test_file):
    print(f"Source file doesn't exist: {source_file}", file=sys.stderr)
    sys.exit(1)

def PrintCallback(id, userData, agent, message):
	print(message)

def ProductionExcisedCallback(id, userData, agent, prodName, instantiation):
	print("removing", prodName)

def ProductionFiredCallback(id, userData, agent, prodName, instantiation):
	print("fired", prodName)

def PhaseExecutedCallback(id, userData, agent, phase):
	print("phase", phase, "executed")

def AgentCreatedCallback(id, userData, agent):
	print(agent.GetAgentName(), "created")

def AgentReinitializedCallback(id, userData, agent):
	print(agent.GetAgentName(), "reinitialized")

def AgentDestroyedCallback(id, userData, agent):
	print("destroying agent", agent.GetAgentName())

def SystemShutdownCallback(id, userData, kernel):
	print("Shutting down kernel", kernel)

def RhsFunctionTest(id, userData, agent, functionName, argument):
	print("Agent", agent.GetAgentName(), "called RHS function", functionName, "with argument(s) '", argument, "' and userData '", userData, "'")
	return "success"

def StructuredTraceCallback(id, userData, agent, pXML):
	print("structured data:", pXML.GenerateXMLString(True))

def UpdateEventCallback(id, userData, kernel, runFlags):
	print("update event fired with flags", runFlags)

def UserMessageCallback(id, userData, agent, clientName, message):
	print("Agent", agent.GetAgentName(), "received usermessage event for clientName '", clientName, "' with message '", message, "'")
	return ""

kernel = Kernel.CreateKernelInNewThread()
if not kernel:
	print('kernel creation failed', file=sys.stderr)
	sys.exit(1)

def __cleanup():
    # Neglecting to shut down the kernel causes a segfault, so we use atexit to guarantee proper cleanup
    global kernel
    if kernel:
        kernel.Shutdown()
        del kernel

atexit.register(__cleanup)

agentCallbackId0 = kernel.RegisterForAgentEvent(smlEVENT_AFTER_AGENT_CREATED, AgentCreatedCallback, None)
agentCallbackId1 = kernel.RegisterForAgentEvent(smlEVENT_BEFORE_AGENT_REINITIALIZED, AgentReinitializedCallback, None)
agentCallbackId2 = kernel.RegisterForAgentEvent(smlEVENT_BEFORE_AGENT_DESTROYED, AgentDestroyedCallback, None)
systemCallbackId = kernel.RegisterForSystemEvent(smlEVENT_BEFORE_SHUTDOWN, SystemShutdownCallback, None)
rhsCallbackId = kernel.AddRhsFunction("RhsFunctionTest", RhsFunctionTest, None)
updateCallbackId = kernel.RegisterForUpdateEvent(smlEVENT_AFTER_ALL_OUTPUT_PHASES, UpdateEventCallback, None)
messageCallbackId = kernel.RegisterForClientMessageEvent("TestMessage", UserMessageCallback, None)

agent = kernel.CreateAgent('Soar1')
if not agent:
	print('agent creation failed', file=sys.stderr)
	sys.exit(1)

printcallbackid = agent.RegisterForPrintEvent(smlEVENT_PRINT, PrintCallback, None)
productionCallbackId = agent.RegisterForProductionEvent(smlEVENT_BEFORE_PRODUCTION_REMOVED, ProductionExcisedCallback, None)
productionCallbackId = agent.RegisterForProductionEvent(smlEVENT_AFTER_PRODUCTION_FIRED, ProductionFiredCallback, None)
runCallbackId = agent.RegisterForRunEvent(smlEVENT_AFTER_PHASE_EXECUTED, PhaseExecutedCallback, None)
structuredCallbackId = agent.RegisterForXMLEvent(smlEVENT_XML_TRACE_OUTPUT, StructuredTraceCallback, None)

#load the TOH productions
result = agent.LoadProductions(str(towers_of_hanoi_file))

#loads a function to test the user-defined RHS function stuff
result = agent.LoadProductions(str(toh_test_file))

kernel.SendClientMessage(agent, "TestMessage", "This is a \"quoted\"\" message")
kernel.UnregisterForClientMessageEvent(messageCallbackId)

agent.RunSelf(2, sml_ELABORATION)

agent.UnregisterForProductionEvent(productionCallbackId)
agent.UnregisterForRunEvent(runCallbackId)

kernel.RunAllAgents(3)

kernel.UnregisterForUpdateEvent(updateCallbackId)

print("")

#set the watch level to 0
result = kernel.ExecuteCommandLine("watch 0", "Soar1")

#excise the monitor production
result = kernel.ExecuteCommandLine("excise towers-of-hanoi*monitor*operator-execution*move-disk", "Soar1")

#run TOH the rest of the way and time it
start = time.time()
result = agent.RunSelfForever()
end = time.time()
print("\nTime in seconds:", end - start)

#the output of "print s1" should contain "^rhstest success"
if kernel.ExecuteCommandLine("print s1", "Soar1").find("^rhstest success") == -1:
	print("\nRHS test FAILED", file=sys.stderr)
	sys.exit(1)

result = kernel.ExecuteCommandLine("init-soar", "Soar1")

kernel.DestroyAgent(agent)

#remove all the remaining kernel callback functions (not required, just to test)
print("Removing callbacks...")
kernel.UnregisterForAgentEvent(agentCallbackId0)
kernel.UnregisterForAgentEvent(agentCallbackId1)
kernel.UnregisterForAgentEvent(agentCallbackId2)
kernel.UnregisterForSystemEvent(systemCallbackId)
kernel.RemoveRhsFunction(rhsCallbackId)

#shutdown the kernel; this makes sure agents are deleted and events fire correctly
kernel.Shutdown()
#delete kernel object
del kernel
