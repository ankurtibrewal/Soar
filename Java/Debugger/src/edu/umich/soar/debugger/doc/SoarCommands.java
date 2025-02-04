/********************************************************************************************
 *
 * SoarCommands.java
 *
 * Created on 	Nov 23, 2003
 *
 * @author 		Doug
 * @version
 *
 * Developed by ThreePenny Software <a href="http://www.threepenny.net">www.threepenny.net</a>
 ********************************************************************************************/
package edu.umich.soar.debugger.doc;

import sml.smlPhase;

/********************************************************************************************
 *
 * Information about strings used for Soar Commands is stored here.
 *
 ********************************************************************************************/
public class SoarCommands
{
    /**
     * At this point we just return the command. But in later versions of the
     * debugger we might use the version of Soar to decide what to return (note
     * the "version" command line command returns the Soar version in use)
     */
    public String getSourceCommand(String arg)
    {
        return "source {" + arg + "}";
    }

    public String getPrintCommand(String arg)
    {
        return "print " + arg;
    }

    public String getPrintDepthCommand(String arg, int depth)
    {
        return "print --depth " + depth + " " + arg;
    }

    public String getPrintInternalCommand(String arg)
    {
        return "print --internal " + arg;
    }

    public String getPrintProductionsCommand()
    {
        return "print --all";
    }

    public String getPrintChunksCommand()
    {
        return "print --chunks";
    }

    public String getPrintJustificationsCommand()
    {
        return "print --justifications";
    }

    public String getPrintStackCommand()
    {
        return "print --stack";
    }

    public String getPrintStateCommand()
    {
        return "print <s>";
    }

    public String getPrintOperatorCommand()
    {
        return "print <o>";
    }

    public String getPrintTopStateCommand()
    {
        return "print <ts>";
    }

    public String getPrintSuperStateCommand()
    {
        return "print <ss>";
    }

    public String getWorkingDirectoryCommand()
    {
        return "pwd";
    }

    public String getChangeDirectoryCommand(String arg)
    {
        return "cd \"" + arg + "\"";
    }

    public String getExciseCommand(String arg)
    {
        return "excise " + arg;
    }

    public String getExciseAllCommand()
    {
        return "excise --all";
    }

    public String getExciseChunksCommand()
    {
        return "excise --chunks";
    }

    public String getExciseTaskCommand()
    {
        return "excise --task";
    }

    public String getExciseUserCommand()
    {
        return "excise --user";
    }

    public String getExciseDefaultCommand()
    {
        return "excise --default";
    }

    public String getEditCommand(String arg)
    {
        return "edit " + arg;
    }

    public String getStopCommand()
    {
        return "stop-soar";
    }

    public String getStopBeforeCommand(smlPhase phase)
    {
        return "soar stop-phase " + getPhaseName(phase);
    }

    public String getGetStopBeforeCommand()
    {
        return "soar stop-phase ";
    } // No phase => get value

    public String getPreferencesCommand(String arg)
    {
        return "preferences " + arg;
    }

    public String getPreferencesNameCommand(String arg)
    {
        return "preferences " + arg + " --names";
    }

    public String getPreferencesObjectCommand(String arg)
    {
        return "preferences " + arg + " --object";
    }

    public String getMatchesCommand(String arg)
    {
        return "matches " + arg;
    }

    public String getMatchesWmesCommand(String arg)
    {
        return "matches " + arg + " --wmes";
    }

    public String getInitSoarCommand()
    {
        return "soar init";
    }

    public String getLoadReteCommand(String arg)
    {
        return "rete-net --load \"" + arg + "\"";
    }

    public String getSaveReteCommand(String arg)
    {
        return "rete-net --save \"" + arg + "\"";
    }

    public String getLogNewCommand(String arg)
    {
        return "output log \"" + arg + "\"";
    }

    public String getLogAppendCommand(String arg)
    {
        return "output log --append \"" + arg + "\"";
    }

    public String getLogCloseCommand()
    {
        return "output log --close";
    }

    public String getLogStatusCommand()
    {
        return "output log --query";
    }

    public String getWatchStatusCommand()
    {
        return "trace";
    }

    public String getWatchDecisionsCommand(boolean state)
    {
        return "trace --decisions" + (state ? "" : " remove");
    }

    public String getWatchPhasesCommand(boolean state)
    {
        return "trace --phases" + (state ? "" : " remove");
    }

    public String getWatchUserProductionsCommand(boolean state)
    {
        return "trace --productions" + (state ? "" : " remove");
    }

    public String getWatchChunksCommand(boolean state)
    {
        return "trace --chunks" + (state ? "" : " remove");
    }

    public String getWatchJustificationsCommand(boolean state)
    {
        return "trace --justifications" + (state ? "" : " remove");
    }

    public String getWatchWmesCommand(boolean state)
    {
        return "trace --wmes" + (state ? "" : " remove");
    }

    public String getWatchPreferencesCommand(boolean state)
    {
        return "trace --preferences" + (state ? "" : " remove");
    }

    public String getWatchLevelCommand(int level)
    {
        return "trace --level " + level;
    }

    public String getWatchWmesNoneCommand()
    {
        return "trace --nowmes";
    }

    public String getWatchWmesTimeTagsCommand()
    {
        return "trace --timetags";
    }

    public String getWatchWmesFullCommand()
    {
        return "trace --fullwmes";
    }

    public String getWatchAliasesCommand(boolean state)
    {
        return "trace --aliases" + (state ? "" : " remove");
    }

    public String getWatchBacktracingCommand(boolean state)
    {
        return "trace --backtracing" + (state ? "" : " remove");
    }

    public String getWatchLearnPrintCommand()
    {
        return "trace --learning print";
    }

    public String getWatchLearnFullCommand()
    {
        return "trace --learning fullprint";
    }

    public String getWatchLearnNoneCommand()
    {
        return "trace --learning noprint";
    }

    public String getWatchLoadingCommand(boolean state)
    {
        return "trace --loading " + (state ? "" : " remove");
    }

    public String getPhaseName(smlPhase phase)
    {
        if (phase == smlPhase.sml_APPLY_PHASE)
            return "apply";
        if (phase == smlPhase.sml_DECISION_PHASE)
            return "decision";
        if (phase == smlPhase.sml_INPUT_PHASE)
            return "input";
        if (phase == smlPhase.sml_OUTPUT_PHASE)
            return "output";
        if (phase == smlPhase.sml_PROPOSAL_PHASE)
            return "propose";
        return "";
    }

    public boolean isRunCommand(String command)
    {
    	//BADBAD this will only work with the default aliases for run commands
    	command = command.trim();
    	if (command.startsWith("time")
    			|| command.startsWith("command-to-file")
    			|| command.startsWith("ctf"))
    	{
    		int x = command.indexOf(" ");
    		if (x < 0)
    			return false;
    		return isRunCommand(command.substring(x));
    	}
    	if (command.equals("run") || command.startsWith("run "))
    		return true;
    	if (command.equals("step") || command.startsWith("step "))
    		return true;
    	if (command.equals("d") || command.startsWith("d "))
    		return true;
    	if (command.equals("e") || command.startsWith("e "))
    		return true;
    	return false;
    }
}
