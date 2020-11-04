package org.txhsl.ppml.api.contract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.StaticArray7;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple7;
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
public class DataSet_sol_DataSet extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b5060405160c080610f7b833981018060405260c081101561003057600080fd5b50805160208083015160408085015160608087015160808089015160a0998a015160078054600160a060020a0319908116331790915560088054600160a060020a03909c169b9091169a909a17909955855160e081018752878152978801859052948701829052918601849052908501869052600096850187905260c0949094018690529185556001919091556002919091556003556004556005819055600655610e9b806100e06000396000f3fe608060405234801561001057600080fd5b5060043610610107576000357c0100000000000000000000000000000000000000000000000000000000900480638da5cb5b116100a9578063c688640111610083578063c688640114610380578063cf88c5061461039d578063d0587a5c146103ba578063eeeac01e1461041b57610107565b80638da5cb5b14610368578063997da8d414610370578063aa8c217c1461037857610107565b80630d8da216116100e55780630d8da216146101e95780630f94a5e5146102295780635727dc5c146103585780637a308a4c1461036057610107565b8063030c2bbf1461010c57806303a507be1461013d5780630b546c2e14610157575b600080fd5b610114610423565b6040805173ffffffffffffffffffffffffffffffffffffffff9092168252519081900360200190f35b61014561043f565b60408051918252519081900360200190f35b6101746004803603602081101561016d57600080fd5b5035610463565b6040805160208082528351818301528351919283929083019185019080838360005b838110156101ae578181015183820152602001610196565b50505050905090810190601f1680156101db5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6101f161050a565b604080519788526020880196909652868601949094526060860192909252608085015260a084015260c0830152519081900360e00190f35b6103566004803603604081101561023f57600080fd5b81019060208101813564010000000081111561025a57600080fd5b82018360208201111561026c57600080fd5b8035906020019184600183028401116401000000008311171561028e57600080fd5b91908080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092959493602081019350359150506401000000008111156102e157600080fd5b8201836020820111156102f357600080fd5b8035906020019184600183028401116401000000008311171561031557600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610522945050505050565b005b610145610640565b610145610645565b610114610669565b610145610685565b61014561068a565b6101746004803603602081101561039657600080fd5b5035610690565b610145600480360360208110156103b357600080fd5b50356106fa565b6103e3600480360360608110156103d057600080fd5b508035906020810135906040013561070f565b604051808260e080838360005b838110156104085781810151838201526020016103f0565b5050505090500191505060405180910390f35b610145610796565b60075473ffffffffffffffffffffffffffffffffffffffff1681565b7f79be667ef9dcbbac55a06295ce870b07029bfcdb2dce28d959f2815b16f8179881565b6000818152600a60209081526040918290206002908101805484516001821615610100026000190190911692909204601f810184900484028301840190945283825260609391929091908301828280156104fe5780601f106104d3576101008083540402835291602001916104fe565b820191906000526020600020905b8154815290600101906020018083116104e157829003601f168201915b50505050509050919050565b60005460015460025460035460045460055460065487565b60085473ffffffffffffffffffffffffffffffffffffffff1633148061055f575060085473ffffffffffffffffffffffffffffffffffffffff1633145b15156105cc57604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152601260248201527f5065726d697373696f6e2064656e6965642e0000000000000000000000000000604482015290519081900360640190fd5b600980546001019081905560408051606081018252848152426020808301919091528183018590526000938452600a81529190922082518051919261061692849290910190610d96565b506020828101516001830155604083015180516106399260028501920190610d96565b5050505050565b600781565b7f483ada7726a3c4655da4fbfc0e1108a8fd17b448a68554199c47d08ffb10d4b881565b60085473ffffffffffffffffffffffffffffffffffffffff1681565b600081565b60095481565b6000818152600a602090815260409182902080548351601f60026000196101006001861615020190931692909204918201849004840281018401909452808452606093928301828280156104fe5780601f106104d3576101008083540402835291602001916104fe565b6000908152600a602052604090206001015490565b610717610e14565b60008060008061073a88600080015460006001015460006401000003d0196107a0565b60025460035492965090945061075b918a919060006401000003d0196107a0565b6040805160e08101825296875260208701959095529385015250506060820152600454608082015260a08101939093525060c0820152919050565b6401000003d01981565b60008060008060006107b78a8a8a60018b8b6107da565b9250925092506107c983838389610853565b945094505050509550959350505050565b600080808815156107f2575086915085905084610847565b8860008060015b831561083e57600184161561081f576108178383838f8f8f8e6108b2565b919450925090505b6002840493506108328c8c8c8c8c610b7b565b919d509b5099506107f9565b91955093509150505b96509650969350505050565b60008060006108628585610cb9565b905060008480151561087057fe5b828309905060008580151561088157fe5b828a09905060008680151561089257fe5b8780151561089c57fe5b8486098a09919a91995090975050505050505050565b60008080891580156108c2575088155b156108d4575085915084905083610b6e565b861580156108e0575085155b156108f2575088915087905086610b6e565b6108fa610e33565b8480151561090457fe5b898a0981528480151561091357fe5b81518a0960208201528480151561092657fe5b86870960408201528480151561093857fe5b6040820151870960608201526040805160808101909152808680151561095a57fe5b60408401518e0981526020018680151561097057fe5b60608401518d0981526020018680151561098657fe5b83518b0981526020018680151561099957fe5b60208401518a099052604081015181519192501415806109c157506060810151602082015114155b1515610a2e57604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152601e60248201527f557365206a6163446f75626c652066756e6374696f6e20696e73746561640000604482015290519081900360640190fd5b610a36610e33565b85801515610a4057fe5b825160408401519088039008815285801515610a5857fe5b602083015160608401519088039008602082015285801515610a7657fe5b81518009604082015285801515610a8957fe5b81516040830151096060820152600086801515610aa257fe5b6060830151880388801515610ab357fe5b6020850151800908905086801515610ac757fe5b87801515610ad157fe5b88801515610adb57fe5b6040850151865109600209880382089050600087801515610af857fe5b88801515610b0257fe5b838a038a801515610b0f57fe5b604087015188510908602085015109905087801515610b2a57fe5b88801515610b3457fe5b6060850151602087015109890382089050600088801515610b5157fe5b89801515610b5b57fe5b8b8f098551099297509095509093505050505b9750975097945050505050565b60008080851515610b93575086915085905084610cae565b600084801515610b9f57fe5b898a099050600085801515610bb057fe5b898a099050600086801515610bc157fe5b898a099050600087801515610bd257fe5b88801515610bdc57fe5b848e096004099050600088801515610bf057fe5b89801515610bfa57fe5b8a801515610c0457fe5b8586098c098a801515610c1357fe5b8760030908905088801515610c2457fe5b89801515610c2e57fe5b8384088a038a801515610c3d57fe5b83840908945088801515610c4d57fe5b89801515610c5757fe5b8a801515610c6157fe5b8687096008098a038a801515610c7357fe5b8b801515610c7d57fe5b888d038608840908935088801515610c9157fe5b89801515610c9b57fe5b8c8e096002099497509295509293505050505b955095509592505050565b60008215801590610cca5750818314155b8015610cd557508115155b1515610d4257604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152600e60248201527f496e76616c6964206e756d626572000000000000000000000000000000000000604482015290519081900360640190fd5b6000600183825b8615610d8b578682811515610d5a57fe5b0490508286801515610d6857fe5b87801515610d7257fe5b8584098803860882890290930397909450919250610d49565b509195945050505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610dd757805160ff1916838001178555610e04565b82800160010185558215610e04579182015b82811115610e04578251825591602001919060010190610de9565b50610e10929150610e52565b5090565b60e0604051908101604052806007906020820280388339509192915050565b6080604051908101604052806004906020820280388339509192915050565b610e6c91905b80821115610e105760008155600101610e58565b9056fea165627a7a72305820325055ea70e9dbda1f451d8fbad096e1597c36c9da10c6bc95a086f75baa56de0029";

    public static final String FUNC_SYSADDR = "sysAddr";

    public static final String FUNC_GX = "GX";

    public static final String FUNC_GETVOLUMEHASH = "getVolumeHash";

    public static final String FUNC_CIPHER = "cipher";

    public static final String FUNC_ADDVOLUME = "addVolume";

    public static final String FUNC_BB = "BB";

    public static final String FUNC_GY = "GY";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_AA = "AA";

    public static final String FUNC_AMOUNT = "amount";

    public static final String FUNC_GETVOLUMENAME = "getVolumeName";

    public static final String FUNC_GETVOLUMETIME = "getVolumeTime";

    public static final String FUNC_GETACCESSKEY = "getAccessKey";

    public static final String FUNC_PP = "PP";

    @Deprecated
    protected DataSet_sol_DataSet(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected DataSet_sol_DataSet(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected DataSet_sol_DataSet(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected DataSet_sol_DataSet(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<Address> sysAddr() {
        final Function function = new Function(FUNC_SYSADDR, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Uint256> GX() {
        final Function function = new Function(FUNC_GX, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Utf8String> getVolumeHash(Uint256 _volume) {
        final Function function = new Function(FUNC_GETVOLUMEHASH, 
                Arrays.<Type>asList(_volume), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Tuple7<Uint256, Uint256, Uint256, Uint256, Uint256, Uint256, Uint256>> cipher() {
        final Function function = new Function(FUNC_CIPHER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple7<Uint256, Uint256, Uint256, Uint256, Uint256, Uint256, Uint256>>(
                new Callable<Tuple7<Uint256, Uint256, Uint256, Uint256, Uint256, Uint256, Uint256>>() {
                    @Override
                    public Tuple7<Uint256, Uint256, Uint256, Uint256, Uint256, Uint256, Uint256> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<Uint256, Uint256, Uint256, Uint256, Uint256, Uint256, Uint256>(
                                (Uint256) results.get(0), 
                                (Uint256) results.get(1), 
                                (Uint256) results.get(2), 
                                (Uint256) results.get(3), 
                                (Uint256) results.get(4), 
                                (Uint256) results.get(5), 
                                (Uint256) results.get(6));
                    }
                });
    }

    public RemoteCall<TransactionReceipt> addVolume(Utf8String _name, Utf8String _hash) {
        final Function function = new Function(
                FUNC_ADDVOLUME, 
                Arrays.<Type>asList(_name, _hash), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Uint256> BB() {
        final Function function = new Function(FUNC_BB, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Uint256> GY() {
        final Function function = new Function(FUNC_GY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Address> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Uint256> AA() {
        final Function function = new Function(FUNC_AA, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Uint256> amount() {
        final Function function = new Function(FUNC_AMOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Utf8String> getVolumeName(Uint256 _volume) {
        final Function function = new Function(FUNC_GETVOLUMENAME, 
                Arrays.<Type>asList(_volume), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Uint256> getVolumeTime(Uint256 _volume) {
        final Function function = new Function(FUNC_GETVOLUMETIME, 
                Arrays.<Type>asList(_volume), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<StaticArray7<Uint256>> getAccessKey(Uint256 _reKey, Uint256 _internalPublicKeyX, Uint256 _internalPublicKeyY) {
        final Function function = new Function(FUNC_GETACCESSKEY, 
                Arrays.<Type>asList(_reKey, _internalPublicKeyX, _internalPublicKeyY), 
                Arrays.<TypeReference<?>>asList(new TypeReference<StaticArray7<Uint256>>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Uint256> PP() {
        final Function function = new Function(FUNC_PP, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    @Deprecated
    public static DataSet_sol_DataSet load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new DataSet_sol_DataSet(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static DataSet_sol_DataSet load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new DataSet_sol_DataSet(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static DataSet_sol_DataSet load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new DataSet_sol_DataSet(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static DataSet_sol_DataSet load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new DataSet_sol_DataSet(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<DataSet_sol_DataSet> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, Address _owner, Uint256 _ex, Uint256 _ey, Uint256 _vx, Uint256 _vy, Uint256 _s) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_owner, _ex, _ey, _vx, _vy, _s));
        return deployRemoteCall(DataSet_sol_DataSet.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<DataSet_sol_DataSet> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, Address _owner, Uint256 _ex, Uint256 _ey, Uint256 _vx, Uint256 _vy, Uint256 _s) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_owner, _ex, _ey, _vx, _vy, _s));
        return deployRemoteCall(DataSet_sol_DataSet.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<DataSet_sol_DataSet> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, Address _owner, Uint256 _ex, Uint256 _ey, Uint256 _vx, Uint256 _vy, Uint256 _s) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_owner, _ex, _ey, _vx, _vy, _s));
        return deployRemoteCall(DataSet_sol_DataSet.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<DataSet_sol_DataSet> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, Address _owner, Uint256 _ex, Uint256 _ey, Uint256 _vx, Uint256 _vy, Uint256 _s) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_owner, _ex, _ey, _vx, _vy, _s));
        return deployRemoteCall(DataSet_sol_DataSet.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
