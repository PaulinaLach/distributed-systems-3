package clientside;

import laboratory.LaboratoryService;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.*;

import java.io.IOException;

public class JavaAsyncClient {

    TTransport transport;

    public LaboratoryService.AsyncClient getClient() throws TTransportException {
        TNonblockingTransport nbtransport = null;
        TAsyncClientManager clientManager = null;
        try {
            nbtransport = new TNonblockingSocket("localhost", 9093);
            clientManager = new TAsyncClientManager();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();
        return new LaboratoryService.AsyncClient(protocolFactory, clientManager, nbtransport);
    }

    public void close() {
        transport.close();
    }

}
