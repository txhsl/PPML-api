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
    private static final String BINARY = "60806040523480156200001157600080fd5b506040805180820190915260048082527f50504d4c00000000000000000000000000000000000000000000000000000000602090920191825262000058916000916200005f565b5062000104565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10620000a257805160ff1916838001178555620000d2565b82800160010185558215620000d2579182015b82811115620000d2578251825591602001919060010190620000b5565b50620000e0929150620000e4565b5090565b6200010191905b80821115620000e05760008155600101620000eb565b90565b6115a480620001146000396000f3fe608060405234801561001057600080fd5b50600436106100bb576000357c010000000000000000000000000000000000000000000000000000000090048063889120f811610083578063889120f814610505578063b099741c146105bf578063dd15149514610675578063dd2f8dd21461071b578063e35cf0ca14610859576100bb565b806306fdde03146100c05780630e3df78a1461013d578063156d081e146101e55780634aaf4a121461039b57806369d0100c1461045d575b600080fd5b6100c86108ff565b6040805160208082528351818301528351919283929083019185019080838360005b838110156101025781810151838201526020016100ea565b50505050905090810190601f16801561012f5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6100c86004803603604081101561015357600080fd5b81019060208101813564010000000081111561016e57600080fd5b82018360208201111561018057600080fd5b803590602001918460018302840111640100000000831117156101a257600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550509135925061098d915050565b610399600480360360608110156101fb57600080fd5b81019060208101813564010000000081111561021657600080fd5b82018360208201111561022857600080fd5b8035906020019184600183028401116401000000008311171561024a57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929594936020810193503591505064010000000081111561029d57600080fd5b8201836020820111156102af57600080fd5b803590602001918460018302840111640100000000831117156102d157600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929594936020810193503591505064010000000081111561032457600080fd5b82018360208201111561033657600080fd5b8035906020019184600183028401116401000000008311171561035857600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610a91945050505050565b005b610441600480360360208110156103b157600080fd5b8101906020810181356401000000008111156103cc57600080fd5b8201836020820111156103de57600080fd5b8035906020019184600183028401116401000000008311171561040057600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610e6d945050505050565b60408051600160a060020a039092168252519081900360200190f35b6100c86004803603604081101561047357600080fd5b81019060208101813564010000000081111561048e57600080fd5b8201836020820111156104a057600080fd5b803590602001918460018302840111640100000000831117156104c257600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295505091359250610ede915050565b6105ad6004803603604081101561051b57600080fd5b81019060208101813564010000000081111561053657600080fd5b82018360208201111561054857600080fd5b8035906020019184600183028401116401000000008311171561056a57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295505091359250610fa8915050565b60408051918252519081900360200190f35b6100c8600480360360408110156105d557600080fd5b600160a060020a03823516919081019060408101602082013564010000000081111561060057600080fd5b82018360208201111561061257600080fd5b8035906020019184600183028401116401000000008311171561063457600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550611022945050505050565b6103996004803603602081101561068b57600080fd5b8101906020810181356401000000008111156106a657600080fd5b8201836020820111156106b857600080fd5b803590602001918460018302840111640100000000831117156106da57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550611102945050505050565b6103996004803603606081101561073157600080fd5b81019060208101813564010000000081111561074c57600080fd5b82018360208201111561075e57600080fd5b8035906020019184600183028401116401000000008311171561078057600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295600160a060020a038535169590949093506040810192506020013590506401000000008111156107e457600080fd5b8201836020820111156107f657600080fd5b8035906020019184600183028401116401000000008311171561081857600080fd5b91908080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092955061124a945050505050565b6105ad6004803603602081101561086f57600080fd5b81019060208101813564010000000081111561088a57600080fd5b82018360208201111561089c57600080fd5b803590602001918460018302840111640100000000831117156108be57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550611472945050505050565b6000805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156109855780601f1061095a57610100808354040283529160200191610985565b820191906000526020600020905b81548152906001019060200180831161096857829003601f168201915b505050505081565b60606001836040518082805190602001908083835b602083106109c15780518252601f1990920191602091820191016109a2565b518151600019602094850361010090810a8201928316921993909316919091179092529490920196875260408051978890038201882060008b81526002918201845282902081018054601f600182161590980290950190941604948501829004820288018201905283875290945091925050830182828015610a845780601f10610a5957610100808354040283529160200191610a84565b820191906000526020600020905b815481529060010190602001808311610a6757829003601f168201915b5050505050905092915050565b6001836040518082805190602001908083835b60208310610ac35780518252601f199092019160209182019101610aa4565b51815160209384036101000a6000190180199092169116179052920194855250604051938490030190922054600160a060020a031633149150610b69905057604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152601260248201527f5065726d697373696f6e2064656e6965642e0000000000000000000000000000604482015290519081900360640190fd5b6001836040518082805190602001908083835b60208310610b9b5780518252601f199092019160209182019101610b7c565b51815160209384036101000a60001901801990921691161790529201948552506040805194859003820185206001908101805482019055606086018252878652428684015285820187905290518851919450889350918291908401908083835b60208310610c1a5780518252601f199092019160209182019101610bfb565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060020160006001866040518082805190602001908083835b60208310610c845780518252601f199092019160209182019101610c65565b51815160209384036101000a60001901801990921691161790529201948552506040805194859003820190942060010154855284810195909552505001600020825180519192610cd9928492909101906114dd565b50602082810151600183015560408301518051610cfc92600285019201906114dd565b509050507fc3de291eeb89128edaa6c06d759763df20504e9534da2ecb860c2c4efe148bfc836001856040518082805190602001908083835b60208310610d545780518252601f199092019160209182019101610d35565b51815160209384036101000a6000190180199092169116179052920194855250604080519485900382018520600101548583018190526060808752875190870152865190958a9550935083929183019160808401919088019080838360005b83811015610dcb578181015183820152602001610db3565b50505050905090810190601f168015610df85780820380516001836020036101000a031916815260200191505b50838103825284518152845160209182019186019080838360005b83811015610e2b578181015183820152602001610e13565b50505050905090810190601f168015610e585780820380516001836020036101000a031916815260200191505b509550505050505060405180910390a1505050565b60006001826040518082805190602001908083835b60208310610ea15780518252601f199092019160209182019101610e82565b51815160209384036101000a6000190180199092169116179052920194855250604051938490030190922054600160a060020a0316949350505050565b60606001836040518082805190602001908083835b60208310610f125780518252601f199092019160209182019101610ef3565b518151600019602094850361010090810a8201928316921993909316919091179092529490920196875260408051978890038201882060008b8152600291820184528290208054601f600182161590980290950190941604948501829004820288018201905283875290945091925050830182828015610a845780601f10610a5957610100808354040283529160200191610a84565b60006001836040518082805190602001908083835b60208310610fdc5780518252601f199092019160209182019101610fbd565b51815160209384036101000a6000190180199092169116179052920194855250604080519485900382019094206000968752600201905250509091206001015492915050565b60606002600084600160a060020a0316600160a060020a03168152602001908152602001600020826040518082805190602001908083835b602083106110795780518252601f19909201916020918201910161105a565b518151600019602094850361010090810a820192831692199390931691909117909252949092019687526040805197889003820188208054601f6002600183161590980290950116959095049283018290048202880182019052818752929450925050830182828015610a845780601f10610a5957610100808354040283529160200191610a84565b604080519081016040528033600160a060020a0316815260200160008152506001826040518082805190602001908083835b602083106111535780518252601f199092019160209182019101611134565b51815160209384036101000a60001901801990921691161790529201948552506040805194859003820185208651815473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a039091161781559582015160019096019590955580845285518482015285517fcd448c18eaeb22d8a2bdee65765801e4c87e17615b80df5ee1b4695b244ee16c958795945084935083019185019080838360005b8381101561120d5781810151838201526020016111f5565b50505050905090810190601f16801561123a5780820380516001836020036101000a031916815260200191505b509250505060405180910390a150565b6001836040518082805190602001908083835b6020831061127c5780518252601f19909201916020918201910161125d565b51815160209384036101000a6000190180199092169116179052920194855250604051938490030190922054600160a060020a031633149150611322905057604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152601260248201527f5065726d697373696f6e2064656e6965642e0000000000000000000000000000604482015290519081900360640190fd5b806002600084600160a060020a0316600160a060020a03168152602001908152602001600020846040518082805190602001908083835b602083106113785780518252601f199092019160209182019101611359565b51815160209384036101000a600019018019909216911617905292019485525060405193849003810190932084516113b995919491909101925090506114dd565b507f1c58f961621614a71280aeb2dfc04618928d6699e45a7551ab0cb33412f200cc8383604051808060200183600160a060020a0316600160a060020a03168152602001828103825284818151815260200191508051906020019080838360005b8381101561143257818101518382015260200161141a565b50505050905090810190601f16801561145f5780820380516001836020036101000a031916815260200191505b50935050505060405180910390a1505050565b60006001826040518082805190602001908083835b602083106114a65780518252601f199092019160209182019101611487565b51815160209384036101000a6000190180199092169116179052920194855250604051938490030190922060010154949350505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061151e57805160ff191683800117855561154b565b8280016001018555821561154b579182015b8281111561154b578251825591602001919060010190611530565b5061155792915061155b565b5090565b61157591905b808211156115575760008155600101611561565b9056fea165627a7a72305820c81f14d0af9124631514e85f7022b767c94e03d332e4d215857ccca5ad7274ab0029";

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

    public RemoteCall<Utf8String> getReEncryptedKey(Address _addr, Utf8String _key) {
        final Function function = new Function(FUNC_GETREENCRYPTEDKEY, 
                Arrays.<Type>asList(_addr, _key), 
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
