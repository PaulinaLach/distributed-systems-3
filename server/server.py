import threading

from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.server import TServer, TNonblockingServer

from examinationHandler import ExaminationHandler
from laboratory import LaboratoryService

if __name__ == '__main__':
    handler = ExaminationHandler()
    processor = LaboratoryService.Processor(handler)

    transport = TSocket.TServerSocket(port=9093)
    tfactory = TTransport.TBufferedTransportFactory()
    pfactory = TBinaryProtocol.TBinaryProtocolFactory()

    # server = TServer.TSimpleServer(processor, transport, tfactory, pfactory)

    # You could do one of these for a multithreaded server
    # server = TServer.TThreadedServer(
    #     processor, transport, tfactory, pfactory)
    # server = TServer.TThreadPoolServer(
    #     processor, transport, tfactory, pfactory)

    server = TNonblockingServer.TNonblockingServer(
        processor, transport)

    print('Starting the server...')
    async_server_thread = threading.Thread(target=server.serve)
    async_server_thread.start()
    # print('done.')


    transport = TSocket.TServerSocket(port=9090)
    tfactory = TTransport.TBufferedTransportFactory()
    pfactory = TBinaryProtocol.TBinaryProtocolFactory()

    # server = TServer.TSimpleServer(processor, transport, tfactory, pfactory)

    # You could do one of these for a multithreaded server
    # server = TServer.TThreadedServer(
    #     processor, transport, tfactory, pfactory)
    server = TServer.TThreadPoolServer(
        processor, transport, tfactory, pfactory)

    # print('Starting the server...')
    server.serve()
    print('done.')
