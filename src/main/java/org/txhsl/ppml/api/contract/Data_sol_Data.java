package org.txhsl.ppml.api.contract;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
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
    private static final String BINARY = "608060405234801561001057600080fd5b5060405161093d38038061093d8339810180604052604081101561003357600080fd5b81516020830180519193928301929164010000000081111561005457600080fd5b8201602081018481111561006757600080fd5b815164010000000081118282018710171561008157600080fd5b50506003805433600160a060020a03199182161790915560028054909116600160a060020a03871617905580519093506100c492506000915060208401906100cc565b505050610167565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061010d57805160ff191683800117855561013a565b8280016001018555821561013a579182015b8281111561013a57825182559160200191906001019061011f565b5061014692915061014a565b5090565b61016491905b808211156101465760008155600101610150565b90565b6107c7806101766000396000f3fe608060405234801561001057600080fd5b506004361061005d577c010000000000000000000000000000000000000000000000000000000060003504631e7c01978114610062578063616ffe83146101915780636e9960c3146102ac575b600080fd5b61018f6004803603604081101561007857600080fd5b81019060208101813564010000000081111561009357600080fd5b8201836020820111156100a557600080fd5b803590602001918460018302840111640100000000831117156100c757600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929594936020810193503591505064010000000081111561011a57600080fd5b82018360208201111561012c57600080fd5b8035906020019184600183028401116401000000008311171561014e57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506102dd945050505050565b005b610237600480360360208110156101a757600080fd5b8101906020810181356401000000008111156101c257600080fd5b8201836020820111156101d457600080fd5b803590602001918460018302840111640100000000831117156101f657600080fd5b91908080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092955061056b945050505050565b6040805160208082528351818301528351919283929083019185019080838360005b83811015610271578181015183820152602001610259565b50505050905090810190601f16801561029e5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6102b46106e6565b6040805173ffffffffffffffffffffffffffffffffffffffff9092168252519081900360200190f35b60035473ffffffffffffffffffffffffffffffffffffffff16331461036357604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152601260248201527f5065726d697373696f6e2064656e6965642e0000000000000000000000000000604482015290519081900360640190fd5b806001836040518082805190602001908083835b602083106103965780518252601f199092019160209182019101610377565b51815160209384036101000a600019018019909216911617905292019485525060405193849003810190932084516103d79591949190910192509050610703565b507f0eec281a8e1b5ca89481b3f27e8b56ea7a646af4b3e872267c729ed06570edd030836001856040518082805190602001908083835b6020831061042d5780518252601f19909201916020918201910161040e565b51815160209384036101000a600019018019909216911617905292019485525060408051948590038201852073ffffffffffffffffffffffffffffffffffffffff88168652606086840181815288519188019190915287519196955093509084019160808501919087019080838360005b838110156104b657818101518382015260200161049e565b50505050905090810190601f1680156104e35780820380516001836020036101000a031916815260200191505b508381038252845460026000196101006001841615020190911604808252602090910190859080156105565780601f1061052b57610100808354040283529160200191610556565b820191906000526020600020905b81548152906001019060200180831161053957829003601f168201915b50509550505050505060405180910390a15050565b60035460609073ffffffffffffffffffffffffffffffffffffffff1633146105f457604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152601260248201527f5065726d697373696f6e2064656e6965642e0000000000000000000000000000604482015290519081900360640190fd5b6001826040518082805190602001908083835b602083106106265780518252601f199092019160209182019101610607565b518151600019602094850361010090810a820192831692199390931691909117909252949092019687526040805197889003820188208054601f60026001831615909802909501169590950492830182900482028801820190528187529294509250508301828280156106da5780601f106106af576101008083540402835291602001916106da565b820191906000526020600020905b8154815290600101906020018083116106bd57829003601f168201915b50505050509050919050565b60025473ffffffffffffffffffffffffffffffffffffffff165b90565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061074457805160ff1916838001178555610771565b82800160010185558215610771579182015b82811115610771578251825591602001919060010190610756565b5061077d929150610781565b5090565b61070091905b8082111561077d576000815560010161078756fea165627a7a72305820387635d1151ac5c717eaea7809312bca48435afbc2a556d98d5bde9aad0710a30029";

    public static final String FUNC_WRITE = "write";

    public static final String FUNC_READ = "read";

    public static final String FUNC_GETADMIN = "getAdmin";

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

    public RemoteCall<TransactionReceipt> write(Utf8String _fileId, Utf8String _hash) {
        final Function function = new Function(
                FUNC_WRITE, 
                Arrays.<Type>asList(_fileId, _hash), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Utf8String> read(Utf8String _fileId) {
        final Function function = new Function(FUNC_READ, 
                Arrays.<Type>asList(_fileId), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Address> getAdmin() {
        final Function function = new Function(FUNC_GETADMIN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public List<DataEventResponse> getDataEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(DATA_EVENT, transactionReceipt);
        ArrayList<DataEventResponse> responses = new ArrayList<DataEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DataEventResponse typedResponse = new DataEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._self = (Address) eventValues.getNonIndexedValues().get(0);
            typedResponse._fileId = (Utf8String) eventValues.getNonIndexedValues().get(1);
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
                typedResponse._fileId = (Utf8String) eventValues.getNonIndexedValues().get(1);
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

        public Utf8String _fileId;

        public Utf8String _hash;
    }
}
