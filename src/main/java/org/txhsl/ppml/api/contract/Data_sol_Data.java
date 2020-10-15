package org.txhsl.ppml.api.contract;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.1.1.
 */
public class Data_sol_Data extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b506040516109523803806109528339810180604052604081101561003357600080fd5b81516020830180519193928301929164010000000081111561005457600080fd5b8201602081018481111561006757600080fd5b815164010000000081118282018710171561008157600080fd5b50506004805433600160a060020a03199182161790915560038054909116600160a060020a03871617905580519093506100c492506000915060208401906100cc565b505050610167565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061010d57805160ff191683800117855561013a565b8280016001018555821561013a579182015b8281111561013a57825182559160200191906001019061011f565b5061014692915061014a565b5090565b61016491905b808211156101465760008155600101610150565b90565b6107dc806101766000396000f3fe608060405234801561001057600080fd5b5060043610610068577c010000000000000000000000000000000000000000000000000000000060003504631e7c0197811461006d5780636e9960c31461019c578063d321fe29146101cd578063ed2e5a97146101e7575b600080fd5b61019a6004803603604081101561008357600080fd5b81019060208101813564010000000081111561009e57600080fd5b8201836020820111156100b057600080fd5b803590602001918460018302840111640100000000831117156100d257600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929594936020810193503591505064010000000081111561012557600080fd5b82018360208201111561013757600080fd5b8035906020019184600183028401116401000000008311171561015957600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506102e9945050505050565b005b6101a4610529565b6040805173ffffffffffffffffffffffffffffffffffffffff9092168252519081900360200190f35b6101d5610546565b60408051918252519081900360200190f35b610204600480360360208110156101fd57600080fd5b503561054c565b604051808060200184815260200180602001838103835286818151815260200191508051906020019080838360005b8381101561024b578181015183820152602001610233565b50505050905090810190601f1680156102785780820380516001836020036101000a031916815260200191505b50838103825284518152845160209182019186019080838360005b838110156102ab578181015183820152602001610293565b50505050905090810190601f1680156102d85780820380516001836020036101000a031916815260200191505b509550505050505060405180910390f35b60045473ffffffffffffffffffffffffffffffffffffffff16331461036f57604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152601260248201527f5065726d697373696f6e2064656e6965642e0000000000000000000000000000604482015290519081900360640190fd5b600280546001908101918290556040805160608101825285815242602080830191909152818301869052600094855292835292208251805191926103b892849290910190610718565b506020828101516001830155604083015180516103db9260028501920190610718565b505060028054600090815260016020818152604092839020835130808252606093820184815283549586161561010002600019019095168790049382018490527f0eec281a8e1b5ca89481b3f27e8b56ea7a646af4b3e872267c729ed06570edd0975095919491850193909290918301906080840190869080156104a05780601f10610475576101008083540402835291602001916104a0565b820191906000526020600020905b81548152906001019060200180831161048357829003601f168201915b50508381038252845460026000196101006001841615020190911604808252602090910190859080156105145780601f106104e957610100808354040283529160200191610514565b820191906000526020600020905b8154815290600101906020018083116104f757829003601f168201915b50509550505050505060405180910390a15050565b60035473ffffffffffffffffffffffffffffffffffffffff165b90565b60025490565b600454606090600090829073ffffffffffffffffffffffffffffffffffffffff1633146105da57604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152601260248201527f5065726d697373696f6e2064656e6965642e0000000000000000000000000000604482015290519081900360640190fd5b60008481526001602081815260409283902080830154815485516002958216156101000260001901909116859004601f8101859004850282018501909652858152919490938501928591908301828280156106765780601f1061064b57610100808354040283529160200191610676565b820191906000526020600020905b81548152906001019060200180831161065957829003601f168201915b5050845460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152959850869450925084019050828280156107045780601f106106d957610100808354040283529160200191610704565b820191906000526020600020905b8154815290600101906020018083116106e757829003601f168201915b505050505090509250925092509193909250565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061075957805160ff1916838001178555610786565b82800160010185558215610786579182015b8281111561078657825182559160200191906001019061076b565b50610792929150610796565b5090565b61054391905b80821115610792576000815560010161079c56fea165627a7a7230582018f4e947f988feb81c59ad87d5f283f34ebbf05f9e9d7826602585dc2fff3d210029";

    public static final String FUNC_WRITE = "write";

    public static final String FUNC_GETADMIN = "getAdmin";

    public static final String FUNC_GETAMOUNT = "getAmount";

    public static final String FUNC_READ = "read";

    public static final Event DATA_EVENT = new Event("data", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected Data_sol_Data(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Data_sol_Data(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Data_sol_Data(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Data_sol_Data(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> write(Utf8String _name, Utf8String _hash) {
        final Function function = new Function(
                FUNC_WRITE, 
                Arrays.<Type>asList(_name, _hash), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Address> getAdmin() {
        final Function function = new Function(FUNC_GETADMIN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Uint256> getAmount() {
        final Function function = new Function(FUNC_GETAMOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Tuple3<Utf8String, Uint256, Utf8String>> read(Uint256 _id) {
        final Function function = new Function(FUNC_READ, 
                Arrays.<Type>asList(_id), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple3<Utf8String, Uint256, Utf8String>>(
                new Callable<Tuple3<Utf8String, Uint256, Utf8String>>() {
                    @Override
                    public Tuple3<Utf8String, Uint256, Utf8String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<Utf8String, Uint256, Utf8String>(
                                (Utf8String) results.get(0), 
                                (Uint256) results.get(1), 
                                (Utf8String) results.get(2));
                    }
                });
    }

    public List<DataEventResponse> getDataEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(DATA_EVENT, transactionReceipt);
        ArrayList<DataEventResponse> responses = new ArrayList<DataEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DataEventResponse typedResponse = new DataEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._self = (Address) eventValues.getNonIndexedValues().get(0);
            typedResponse._name = (Utf8String) eventValues.getNonIndexedValues().get(1);
            typedResponse._hash = (Utf8String) eventValues.getNonIndexedValues().get(2);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<DataEventResponse> dataEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, DataEventResponse>() {
            @Override
            public DataEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(DATA_EVENT, log);
                DataEventResponse typedResponse = new DataEventResponse();
                typedResponse.log = log;
                typedResponse._self = (Address) eventValues.getNonIndexedValues().get(0);
                typedResponse._name = (Utf8String) eventValues.getNonIndexedValues().get(1);
                typedResponse._hash = (Utf8String) eventValues.getNonIndexedValues().get(2);
                return typedResponse;
            }
        });
    }

    public Flowable<DataEventResponse> dataEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DATA_EVENT));
        return dataEventFlowable(filter);
    }

    @Deprecated
    public static Data_sol_Data load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Data_sol_Data(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Data_sol_Data load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Data_sol_Data(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Data_sol_Data load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Data_sol_Data(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Data_sol_Data load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Data_sol_Data(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Data_sol_Data> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, Address _admin, Utf8String _name) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_admin, _name));
        return deployRemoteCall(Data_sol_Data.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<Data_sol_Data> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, Address _admin, Utf8String _name) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_admin, _name));
        return deployRemoteCall(Data_sol_Data.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Data_sol_Data> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, Address _admin, Utf8String _name) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_admin, _name));
        return deployRemoteCall(Data_sol_Data.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Data_sol_Data> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, Address _admin, Utf8String _name) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_admin, _name));
        return deployRemoteCall(Data_sol_Data.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class DataEventResponse {
        public Log log;

        public Address _self;

        public Utf8String _name;

        public Utf8String _hash;
    }
}
