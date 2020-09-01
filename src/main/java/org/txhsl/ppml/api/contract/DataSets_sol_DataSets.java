package org.txhsl.ppml.api.contract;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
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
public class DataSets_sol_DataSets extends Contract {
    private static final String BINARY = "60806040523480156200001157600080fd5b506040805180820190915260048082527f50504d4c00000000000000000000000000000000000000000000000000000000602090920191825262000058916000916200005f565b5062000104565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10620000a257805160ff1916838001178555620000d2565b82800160010185558215620000d2579182015b82811115620000d2578251825591602001919060010190620000b5565b50620000e0929150620000e4565b5090565b6200010191905b80821115620000e05760008155600101620000eb565b90565b6115cb80620001146000396000f3fe608060405234801561001057600080fd5b50600436106100bb576000357c010000000000000000000000000000000000000000000000000000000090048063889120f811610083578063889120f814610505578063aade7bfc146105bf578063dd15149514610665578063dd2f8dd21461070b578063e35cf0ca14610849576100bb565b806306fdde03146100c05780630e3df78a1461013d578063156d081e146101e55780634aaf4a121461039b57806369d0100c1461045d575b600080fd5b6100c86108ef565b6040805160208082528351818301528351919283929083019185019080838360005b838110156101025781810151838201526020016100ea565b50505050905090810190601f16801561012f5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6100c86004803603604081101561015357600080fd5b81019060208101813564010000000081111561016e57600080fd5b82018360208201111561018057600080fd5b803590602001918460018302840111640100000000831117156101a257600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550509135925061097d915050565b610399600480360360608110156101fb57600080fd5b81019060208101813564010000000081111561021657600080fd5b82018360208201111561022857600080fd5b8035906020019184600183028401116401000000008311171561024a57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929594936020810193503591505064010000000081111561029d57600080fd5b8201836020820111156102af57600080fd5b803590602001918460018302840111640100000000831117156102d157600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929594936020810193503591505064010000000081111561032457600080fd5b82018360208201111561033657600080fd5b8035906020019184600183028401116401000000008311171561035857600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610a81945050505050565b005b610441600480360360208110156103b157600080fd5b8101906020810181356401000000008111156103cc57600080fd5b8201836020820111156103de57600080fd5b8035906020019184600183028401116401000000008311171561040057600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610e5d945050505050565b60408051600160a060020a039092168252519081900360200190f35b6100c86004803603604081101561047357600080fd5b81019060208101813564010000000081111561048e57600080fd5b8201836020820111156104a057600080fd5b803590602001918460018302840111640100000000831117156104c257600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295505091359250610ece915050565b6105ad6004803603604081101561051b57600080fd5b81019060208101813564010000000081111561053657600080fd5b82018360208201111561054857600080fd5b8035906020019184600183028401116401000000008311171561056a57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295505091359250610f98915050565b60408051918252519081900360200190f35b6100c8600480360360208110156105d557600080fd5b8101906020810181356401000000008111156105f057600080fd5b82018360208201111561060257600080fd5b8035906020019184600183028401116401000000008311171561062457600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550611012945050505050565b6103996004803603602081101561067b57600080fd5b81019060208101813564010000000081111561069657600080fd5b8201836020820111156106a857600080fd5b803590602001918460018302840111640100000000831117156106ca57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550611129945050505050565b6103996004803603606081101561072157600080fd5b81019060208101813564010000000081111561073c57600080fd5b82018360208201111561074e57600080fd5b8035906020019184600183028401116401000000008311171561077057600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295600160a060020a038535169590949093506040810192506020013590506401000000008111156107d457600080fd5b8201836020820111156107e657600080fd5b8035906020019184600183028401116401000000008311171561080857600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550611271945050505050565b6105ad6004803603602081101561085f57600080fd5b81019060208101813564010000000081111561087a57600080fd5b82018360208201111561088c57600080fd5b803590602001918460018302840111640100000000831117156108ae57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550611499945050505050565b6000805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156109755780601f1061094a57610100808354040283529160200191610975565b820191906000526020600020905b81548152906001019060200180831161095857829003601f168201915b505050505081565b60606001836040518082805190602001908083835b602083106109b15780518252601f199092019160209182019101610992565b518151600019602094850361010090810a8201928316921993909316919091179092529490920196875260408051978890038201882060008b81526002918201845282902081018054601f600182161590980290950190941604948501829004820288018201905283875290945091925050830182828015610a745780601f10610a4957610100808354040283529160200191610a74565b820191906000526020600020905b815481529060010190602001808311610a5757829003601f168201915b5050505050905092915050565b6001836040518082805190602001908083835b60208310610ab35780518252601f199092019160209182019101610a94565b51815160209384036101000a6000190180199092169116179052920194855250604051938490030190922054600160a060020a031633149150610b59905057604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152601260248201527f5065726d697373696f6e2064656e6965642e0000000000000000000000000000604482015290519081900360640190fd5b6001836040518082805190602001908083835b60208310610b8b5780518252601f199092019160209182019101610b6c565b51815160209384036101000a60001901801990921691161790529201948552506040805194859003820185206001908101805482019055606086018252878652428684015285820187905290518851919450889350918291908401908083835b60208310610c0a5780518252601f199092019160209182019101610beb565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060020160006001866040518082805190602001908083835b60208310610c745780518252601f199092019160209182019101610c55565b51815160209384036101000a60001901801990921691161790529201948552506040805194859003820190942060010154855284810195909552505001600020825180519192610cc992849290910190611504565b50602082810151600183015560408301518051610cec9260028501920190611504565b509050507fc3de291eeb89128edaa6c06d759763df20504e9534da2ecb860c2c4efe148bfc836001856040518082805190602001908083835b60208310610d445780518252601f199092019160209182019101610d25565b51815160209384036101000a6000190180199092169116179052920194855250604080519485900382018520600101548583018190526060808752875190870152865190958a9550935083929183019160808401919088019080838360005b83811015610dbb578181015183820152602001610da3565b50505050905090810190601f168015610de85780820380516001836020036101000a031916815260200191505b50838103825284518152845160209182019186019080838360005b83811015610e1b578181015183820152602001610e03565b50505050905090810190601f168015610e485780820380516001836020036101000a031916815260200191505b509550505050505060405180910390a1505050565b60006001826040518082805190602001908083835b60208310610e915780518252601f199092019160209182019101610e72565b51815160209384036101000a6000190180199092169116179052920194855250604051938490030190922054600160a060020a0316949350505050565b60606001836040518082805190602001908083835b60208310610f025780518252601f199092019160209182019101610ee3565b518151600019602094850361010090810a8201928316921993909316919091179092529490920196875260408051978890038201882060008b8152600291820184528290208054601f600182161590980290950190941604948501829004820288018201905283875290945091925050830182828015610a745780601f10610a4957610100808354040283529160200191610a74565b60006001836040518082805190602001908083835b60208310610fcc5780518252601f199092019160209182019101610fad565b51815160209384036101000a6000190180199092169116179052920194855250604080519485900382019094206000968752600201905250509091206001015492915050565b60606002600033600160a060020a0316600160a060020a03168152602001908152602001600020826040518082805190602001908083835b602083106110695780518252601f19909201916020918201910161104a565b518151600019602094850361010090810a820192831692199390931691909117909252949092019687526040805197889003820188208054601f600260018316159098029095011695909504928301829004820288018201905281875292945092505083018282801561111d5780601f106110f25761010080835404028352916020019161111d565b820191906000526020600020905b81548152906001019060200180831161110057829003601f168201915b50505050509050919050565b604080519081016040528033600160a060020a0316815260200160008152506001826040518082805190602001908083835b6020831061117a5780518252601f19909201916020918201910161115b565b51815160209384036101000a60001901801990921691161790529201948552506040805194859003820185208651815473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a039091161781559582015160019096019590955580845285518482015285517fcd448c18eaeb22d8a2bdee65765801e4c87e17615b80df5ee1b4695b244ee16c958795945084935083019185019080838360005b8381101561123457818101518382015260200161121c565b50505050905090810190601f1680156112615780820380516001836020036101000a031916815260200191505b509250505060405180910390a150565b6001836040518082805190602001908083835b602083106112a35780518252601f199092019160209182019101611284565b51815160209384036101000a6000190180199092169116179052920194855250604051938490030190922054600160a060020a031633149150611349905057604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152601260248201527f5065726d697373696f6e2064656e6965642e0000000000000000000000000000604482015290519081900360640190fd5b806002600084600160a060020a0316600160a060020a03168152602001908152602001600020846040518082805190602001908083835b6020831061139f5780518252601f199092019160209182019101611380565b51815160209384036101000a600019018019909216911617905292019485525060405193849003810190932084516113e09591949190910192509050611504565b507f1c58f961621614a71280aeb2dfc04618928d6699e45a7551ab0cb33412f200cc8383604051808060200183600160a060020a0316600160a060020a03168152602001828103825284818151815260200191508051906020019080838360005b83811015611459578181015183820152602001611441565b50505050905090810190601f1680156114865780820380516001836020036101000a031916815260200191505b50935050505060405180910390a1505050565b60006001826040518082805190602001908083835b602083106114cd5780518252601f1990920191602091820191016114ae565b51815160209384036101000a6000190180199092169116179052920194855250604051938490030190922060010154949350505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061154557805160ff1916838001178555611572565b82800160010185558215611572579182015b82811115611572578251825591602001919060010190611557565b5061157e929150611582565b5090565b61159c91905b8082111561157e5760008155600101611588565b9056fea165627a7a72305820a3d5089679876f7656c33a0d71ea5887a445c6f626f9892170cbaac8d2b8948d0029";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_GETVOLUMEHASH = "getVolumeHash";

    public static final String FUNC_ADDVOLUME = "addVolume";

    public static final String FUNC_GETOWNER = "getOwner";

    public static final String FUNC_GETVOLUMENAME = "getVolumeName";

    public static final String FUNC_GETVOLUMETIME = "getVolumeTime";

    public static final String FUNC_GETREENCRYPTEDKEY = "getReEncryptedKey";

    public static final String FUNC_CREATEDATASET = "createDataSet";

    public static final String FUNC_SHAREKEY = "shareKey";

    public static final String FUNC_GETAMOUNT = "getAmount";

    public static final Event SHAREKEY_EVENT = new Event("ShareKey", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event ADDVOLUME_EVENT = new Event("AddVolume", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event CREATEDATASET_EVENT = new Event("CreateDataSet", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected DataSets_sol_DataSets(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected DataSets_sol_DataSets(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected DataSets_sol_DataSets(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected DataSets_sol_DataSets(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<Utf8String> name() {
        final Function function = new Function(FUNC_NAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Utf8String> getVolumeHash(Utf8String _key, Uint256 _volume) {
        final Function function = new Function(FUNC_GETVOLUMEHASH, 
                Arrays.<Type>asList(_key, _volume), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<TransactionReceipt> addVolume(Utf8String _key, Utf8String _name, Utf8String _hash) {
        final Function function = new Function(
                FUNC_ADDVOLUME, 
                Arrays.<Type>asList(_key, _name, _hash), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Address> getOwner(Utf8String _key) {
        final Function function = new Function(FUNC_GETOWNER, 
                Arrays.<Type>asList(_key), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Utf8String> getVolumeName(Utf8String _key, Uint256 _volume) {
        final Function function = new Function(FUNC_GETVOLUMENAME, 
                Arrays.<Type>asList(_key, _volume), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Uint256> getVolumeTime(Utf8String _key, Uint256 _volume) {
        final Function function = new Function(FUNC_GETVOLUMETIME, 
                Arrays.<Type>asList(_key, _volume), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Utf8String> getReEncryptedKey(Utf8String _key) {
        final Function function = new Function(FUNC_GETREENCRYPTEDKEY, 
                Arrays.<Type>asList(_key), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<TransactionReceipt> createDataSet(Utf8String _key) {
        final Function function = new Function(
                FUNC_CREATEDATASET, 
                Arrays.<Type>asList(_key), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> shareKey(Utf8String _key, Address _to, Utf8String _reEncryptedKey) {
        final Function function = new Function(
                FUNC_SHAREKEY, 
                Arrays.<Type>asList(_key, _to, _reEncryptedKey), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Uint256> getAmount(Utf8String _key) {
        final Function function = new Function(FUNC_GETAMOUNT, 
                Arrays.<Type>asList(_key), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public List<ShareKeyEventResponse> getShareKeyEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(SHAREKEY_EVENT, transactionReceipt);
        ArrayList<ShareKeyEventResponse> responses = new ArrayList<ShareKeyEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ShareKeyEventResponse typedResponse = new ShareKeyEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._key = (Utf8String) eventValues.getNonIndexedValues().get(0);
            typedResponse._to = (Address) eventValues.getNonIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ShareKeyEventResponse> shareKeyEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, ShareKeyEventResponse>() {
            @Override
            public ShareKeyEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(SHAREKEY_EVENT, log);
                ShareKeyEventResponse typedResponse = new ShareKeyEventResponse();
                typedResponse.log = log;
                typedResponse._key = (Utf8String) eventValues.getNonIndexedValues().get(0);
                typedResponse._to = (Address) eventValues.getNonIndexedValues().get(1);
                return typedResponse;
            }
        });
    }

    public Flowable<ShareKeyEventResponse> shareKeyEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SHAREKEY_EVENT));
        return shareKeyEventFlowable(filter);
    }

    public List<AddVolumeEventResponse> getAddVolumeEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ADDVOLUME_EVENT, transactionReceipt);
        ArrayList<AddVolumeEventResponse> responses = new ArrayList<AddVolumeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AddVolumeEventResponse typedResponse = new AddVolumeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._key = (Utf8String) eventValues.getNonIndexedValues().get(0);
            typedResponse._volume = (Uint256) eventValues.getNonIndexedValues().get(1);
            typedResponse._name = (Utf8String) eventValues.getNonIndexedValues().get(2);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AddVolumeEventResponse> addVolumeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, AddVolumeEventResponse>() {
            @Override
            public AddVolumeEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ADDVOLUME_EVENT, log);
                AddVolumeEventResponse typedResponse = new AddVolumeEventResponse();
                typedResponse.log = log;
                typedResponse._key = (Utf8String) eventValues.getNonIndexedValues().get(0);
                typedResponse._volume = (Uint256) eventValues.getNonIndexedValues().get(1);
                typedResponse._name = (Utf8String) eventValues.getNonIndexedValues().get(2);
                return typedResponse;
            }
        });
    }

    public Flowable<AddVolumeEventResponse> addVolumeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ADDVOLUME_EVENT));
        return addVolumeEventFlowable(filter);
    }

    public List<CreateDataSetEventResponse> getCreateDataSetEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CREATEDATASET_EVENT, transactionReceipt);
        ArrayList<CreateDataSetEventResponse> responses = new ArrayList<CreateDataSetEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CreateDataSetEventResponse typedResponse = new CreateDataSetEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._key = (Utf8String) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CreateDataSetEventResponse> createDataSetEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, CreateDataSetEventResponse>() {
            @Override
            public CreateDataSetEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CREATEDATASET_EVENT, log);
                CreateDataSetEventResponse typedResponse = new CreateDataSetEventResponse();
                typedResponse.log = log;
                typedResponse._key = (Utf8String) eventValues.getNonIndexedValues().get(0);
                return typedResponse;
            }
        });
    }

    public Flowable<CreateDataSetEventResponse> createDataSetEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CREATEDATASET_EVENT));
        return createDataSetEventFlowable(filter);
    }

    @Deprecated
    public static DataSets_sol_DataSets load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new DataSets_sol_DataSets(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static DataSets_sol_DataSets load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new DataSets_sol_DataSets(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static DataSets_sol_DataSets load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new DataSets_sol_DataSets(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static DataSets_sol_DataSets load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new DataSets_sol_DataSets(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<DataSets_sol_DataSets> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(DataSets_sol_DataSets.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<DataSets_sol_DataSets> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(DataSets_sol_DataSets.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<DataSets_sol_DataSets> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(DataSets_sol_DataSets.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<DataSets_sol_DataSets> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(DataSets_sol_DataSets.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class ShareKeyEventResponse {
        public Log log;

        public Utf8String _key;

        public Address _to;
    }

    public static class AddVolumeEventResponse {
        public Log log;

        public Utf8String _key;

        public Uint256 _volume;

        public Utf8String _name;
    }

    public static class CreateDataSetEventResponse {
        public Log log;

        public Utf8String _key;
    }
}
