project(inferenceEngineBenchmark)

cmake_minimum_required(VERSION 3.16)

include_directories(.)
include_directories(CLI/src)
include_directories(ClientSML/src)
include_directories(ConnectionSML/src)
include_directories(ElementXML/src)
include_directories(KernelSML/src)
include_directories(SoarKernel/src/debug_code)
include_directories(SoarKernel/src/decision_process)
include_directories(SoarKernel/src/episodic_memory)
include_directories(SoarKernel/src/explanation_based_chunking)
include_directories(SoarKernel/src/explanation_memory)
include_directories(SoarKernel/src/interface)
include_directories(SoarKernel/src/output_manager)
include_directories(SoarKernel/src/parsing)
include_directories(SoarKernel/src/reinforcement_learning)
include_directories(SoarKernel/src/semantic_memory)
include_directories(SoarKernel/src/shared)
include_directories(SoarKernel/src/soar_representation)
include_directories(SoarKernel/src/visualizer)
include_directories(SVS/src)
include_directories(SVS/src/posix)
include_directories(SVS/eigen)
include_directories(SVS/ccd)
include_directories(shared/)


file(GLOB SRC CONFIGURE_DEPENDS 
    CLI/CommandLineInterface.cxx
    ClientSML/ClientSML.cxx
    ConnectionSML/ConnectionSML.cxx
    ElementXML/ElementXML.cxx
    KernelSML/KernelSML.cxx
    SoarKernel/SoarKernel.cxx
    SoarKernel/sqlite/sqlite3.c
    SVS/SVS.cxx
    SVS/ccd/ccd.c SVS/ccd/mpr.c SVS/ccd/polytope.c SVS/ccd/support.c SVS/ccd/vec3.c
    SVS/src/posix/platform_specific.cpp
    shared/shared.cxx
)
message(STATUS ${SRC})

add_library(Soar SHARED ${SRC} )
#target_link_libraries(inferenceEngineBenchmark Soar pthread dl)
