package clientside;
import laboratory.*;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransportException;

public class JavaClient {

    TTransport transport;

    public LaboratoryService.Client getClient() throws TTransportException {
        transport = new TSocket("localhost", 9090);
        transport.open();

        TProtocol protocol = new TBinaryProtocol(transport);
        LaboratoryService.Client client = new LaboratoryService.Client(protocol);

        return client;
    }

    public void close() {
        transport.close();
    }

}
