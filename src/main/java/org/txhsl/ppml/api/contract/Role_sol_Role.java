package org.txhsl.ppml.api.contract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
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
public class Role_sol_Role extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b5060405162000f2138038062000f21833981018060405260e081101561003557600080fd5b81516020830180519193928301929164010000000081111561005657600080fd5b8201602081018481111561006957600080fd5b815164010000000081118282018710171561008357600080fd5b505060208281015160408401516060850151608086015160a0909601516009805433600160a060020a03199182161790915560088054909116600160a060020a038c1617905585519598509296509094909390926100e691600091890190610148565b506040805160e081018252868152602081018690529081018490526060810183905260808101829052600060a0820181905260c09091018190526001959095556002939093556003919091556004556005556006819055600755506101e39050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061018957805160ff19168380011785556101b6565b828001600101855582156101b6579182015b828111156101b657825182559160200191906001019061019b565b506101c29291506101c6565b5090565b6101e091905b808211156101c257600081556001016101cc565b90565b610d2e80620001f36000396000f3fe608060405234801561001057600080fd5b5060043610610107576000357c0100000000000000000000000000000000000000000000000000000000900480639813d3a8116100a9578063d0587a5c11610083578063d0587a5c14610306578063ed4f37301461032f578063eeeac01e14610369578063f851a4401461037157610107565b80639813d3a81461027a578063997da8d4146102a0578063c64be074146102a857610107565b8063484d59e6116100e5578063484d59e61461018a5780635727dc5c1461020757806360849a8a1461020f5780637a308a4c1461027257610107565b8063030c2bbf1461010c57806303a507be146101305780630d8da2161461014a575b600080fd5b610114610379565b60408051600160a060020a039092168252519081900360200190f35b610138610388565b60408051918252519081900360200190f35b6101526103ac565b604080519788526020880196909652868601949094526060860192909252608085015260a084015260c0830152519081900360e00190f35b6101926103c4565b6040805160208082528351818301528351919283929083019185019080838360005b838110156101cc5781810151838201526020016101b4565b50505050905090810190601f1680156101f95780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b610138610452565b610270600480360361010081101561022657600080fd5b6040805160e0818101909252600160a060020a0384351693928301929161010083019190602084019060079083908390808284376000920191909152509194506104579350505050565b005b6101386104e2565b6102706004803603602081101561029057600080fd5b5035600160a060020a0316610506565b61013861058c565b6102ce600480360360208110156102be57600080fd5b5035600160a060020a0316610591565b604051808260e080838360005b838110156102f35781810151838201526020016102db565b5050505090500191505060405180910390f35b6102ce6004803603606081101561031c57600080fd5b50803590602081013590604001356105e3565b6103556004803603602081101561034557600080fd5b5035600160a060020a031661066a565b604080519115158252519081900360200190f35b610138610688565b610114610692565b600954600160a060020a031681565b7f79be667ef9dcbbac55a06295ce870b07029bfcdb2dce28d959f2815b16f8179881565b60015460025460035460045460055460065460075487565b6000805460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152929183018282801561044a5780601f1061041f5761010080835404028352916020019161044a565b820191906000526020600020905b81548152906001019060200180831161042d57829003601f168201915b505050505081565b600781565b600954600160a060020a031633146104b9576040805160e560020a62461bcd02815260206004820152601260248201527f5065726d697373696f6e2064656e6965642e0000000000000000000000000000604482015290519081900360640190fd5b600160a060020a0382166000908152600b602052604090206104dd90826007610c69565b505050565b7f483ada7726a3c4655da4fbfc0e1108a8fd17b448a68554199c47d08ffb10d4b881565b600954600160a060020a03163314610568576040805160e560020a62461bcd02815260206004820152601260248201527f5065726d697373696f6e2064656e6965642e0000000000000000000000000000604482015290519081900360640190fd5b600160a060020a03166000908152600a60205260409020805460ff19166001179055565b600081565b610599610ca7565b600160a060020a0382166000908152600b602052604090819020815160e08101928390529160079082845b8154815260200190600101908083116105c45750505050509050919050565b6105eb610ca7565b60008060008061060e88600160000154600180015460006401000003d0196106a1565b60035460045492965090945061062f918a919060006401000003d0196106a1565b6040805160e08101825296875260208701959095529385015250506060820152600554608082015260a08101939093525060c0820152919050565b600160a060020a03166000908152600a602052604090205460ff1690565b6401000003d01981565b600854600160a060020a031681565b60008060008060006106b88a8a8a60018b8b6106db565b9250925092506106ca83838389610754565b945094505050509550959350505050565b600080808815156106f3575086915085905084610748565b8860008060015b831561073f576001841615610720576107188383838f8f8f8e6107b3565b919450925090505b6002840493506107338c8c8c8c8c610a65565b919d509b5099506106fa565b91955093509150505b96509650969350505050565b60008060006107638585610ba3565b905060008480151561077157fe5b828309905060008580151561078257fe5b828a09905060008680151561079357fe5b8780151561079d57fe5b8486098a09919a91995090975050505050505050565b60008080891580156107c3575088155b156107d5575085915084905083610a58565b861580156107e1575085155b156107f3575088915087905086610a58565b6107fb610cc6565b8480151561080557fe5b898a0981528480151561081457fe5b81518a0960208201528480151561082757fe5b86870960408201528480151561083957fe5b6040820151870960608201526040805160808101909152808680151561085b57fe5b60408401518e0981526020018680151561087157fe5b60608401518d0981526020018680151561088757fe5b83518b0981526020018680151561089a57fe5b60208401518a099052604081015181519192501415806108c257506060810151602082015114155b1515610918576040805160e560020a62461bcd02815260206004820152601e60248201527f557365206a6163446f75626c652066756e6374696f6e20696e73746561640000604482015290519081900360640190fd5b610920610cc6565b8580151561092a57fe5b82516040840151908803900881528580151561094257fe5b60208301516060840151908803900860208201528580151561096057fe5b8151800960408201528580151561097357fe5b8151604083015109606082015260008680151561098c57fe5b606083015188038880151561099d57fe5b60208501518009089050868015156109b157fe5b878015156109bb57fe5b888015156109c557fe5b60408501518651096002098803820890506000878015156109e257fe5b888015156109ec57fe5b838a038a8015156109f957fe5b604087015188510908602085015109905087801515610a1457fe5b88801515610a1e57fe5b6060850151602087015109890382089050600088801515610a3b57fe5b89801515610a4557fe5b8b8f098551099297509095509093505050505b9750975097945050505050565b60008080851515610a7d575086915085905084610b98565b600084801515610a8957fe5b898a099050600085801515610a9a57fe5b898a099050600086801515610aab57fe5b898a099050600087801515610abc57fe5b88801515610ac657fe5b848e096004099050600088801515610ada57fe5b89801515610ae457fe5b8a801515610aee57fe5b8586098c098a801515610afd57fe5b8760030908905088801515610b0e57fe5b89801515610b1857fe5b8384088a038a801515610b2757fe5b83840908945088801515610b3757fe5b89801515610b4157fe5b8a801515610b4b57fe5b8687096008098a038a801515610b5d57fe5b8b801515610b6757fe5b888d038608840908935088801515610b7b57fe5b89801515610b8557fe5b8c8e096002099497509295509293505050505b955095509592505050565b60008215801590610bb45750818314155b8015610bbf57508115155b1515610c15576040805160e560020a62461bcd02815260206004820152600e60248201527f496e76616c6964206e756d626572000000000000000000000000000000000000604482015290519081900360640190fd5b6000600183825b8615610c5e578682811515610c2d57fe5b0490508286801515610c3b57fe5b87801515610c4557fe5b8584098803860882890290930397909450919250610c1c565b509195945050505050565b8260078101928215610c97579160200282015b82811115610c97578251825591602001919060010190610c7c565b50610ca3929150610ce5565b5090565b60e0604051908101604052806007906020820280388339509192915050565b6080604051908101604052806004906020820280388339509192915050565b610cff91905b80821115610ca35760008155600101610ceb565b9056fea165627a7a723058209ce25cb1ef18661bdc0e99eb2c535af3de106f259b68cdaa76422158fb31ccd40029";

    public static final String FUNC_SYSADDR = "sysAddr";

    public static final String FUNC_GX = "GX";

    public static final String FUNC_CIPHER = "cipher";

    public static final String FUNC_PUBKEY = "pubkey";

    public static final String FUNC_BB = "BB";

    public static final String FUNC_ADDREADING = "addReading";

    public static final String FUNC_GY = "GY";

    public static final String FUNC_ADDWRITING = "addWriting";

    public static final String FUNC_AA = "AA";

    public static final String FUNC_GETREADING = "getReading";

    public static final String FUNC_GETACCESSKEY = "getAccessKey";

    public static final String FUNC_GETWRITING = "getWriting";

    public static final String FUNC_PP = "PP";

    public static final String FUNC_ADMIN = "admin";

    @Deprecated
    protected Role_sol_Role(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Role_sol_Role(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Role_sol_Role(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Role_sol_Role(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
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

    public RemoteCall<Utf8String> pubkey() {
        final Function function = new Function(FUNC_PUBKEY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Uint256> BB() {
        final Function function = new Function(FUNC_BB, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<TransactionReceipt> addReading(Address _dcAddr, StaticArray7<Uint256> _key) {
        final Function function = new Function(
                FUNC_ADDREADING, 
                Arrays.<Type>asList(_dcAddr, _key), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Uint256> GY() {
        final Function function = new Function(FUNC_GY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<TransactionReceipt> addWriting(Address _dcAddr) {
        final Function function = new Function(
                FUNC_ADDWRITING, 
                Arrays.<Type>asList(_dcAddr), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Uint256> AA() {
        final Function function = new Function(FUNC_AA, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<StaticArray7<Uint256>> getReading(Address _dcAddr) {
        final Function function = new Function(FUNC_GETREADING, 
                Arrays.<Type>asList(_dcAddr), 
                Arrays.<TypeReference<?>>asList(new TypeReference<StaticArray7<Uint256>>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<StaticArray7<Uint256>> getAccessKey(Uint256 _reKey, Uint256 _internalPublicKeyX, Uint256 _internalPublicKeyY) {
        final Function function = new Function(FUNC_GETACCESSKEY, 
                Arrays.<Type>asList(_reKey, _internalPublicKeyX, _internalPublicKeyY), 
                Arrays.<TypeReference<?>>asList(new TypeReference<StaticArray7<Uint256>>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Bool> getWriting(Address _dcAddr) {
        final Function function = new Function(FUNC_GETWRITING, 
                Arrays.<Type>asList(_dcAddr), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Uint256> PP() {
        final Function function = new Function(FUNC_PP, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Address> admin() {
        final Function function = new Function(FUNC_ADMIN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    @Deprecated
    public static Role_sol_Role load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Role_sol_Role(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Role_sol_Role load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Role_sol_Role(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Role_sol_Role load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Role_sol_Role(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Role_sol_Role load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Role_sol_Role(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Role_sol_Role> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, Address _admin, Utf8String _pubkey, Uint256 _ex, Uint256 _ey, Uint256 _vx, Uint256 _vy, Uint256 _s) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_admin, _pubkey, _ex, _ey, _vx, _vy, _s));
        return deployRemoteCall(Role_sol_Role.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<Role_sol_Role> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, Address _admin, Utf8String _pubkey, Uint256 _ex, Uint256 _ey, Uint256 _vx, Uint256 _vy, Uint256 _s) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_admin, _pubkey, _ex, _ey, _vx, _vy, _s));
        return deployRemoteCall(Role_sol_Role.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Role_sol_Role> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, Address _admin, Utf8String _pubkey, Uint256 _ex, Uint256 _ey, Uint256 _vx, Uint256 _vy, Uint256 _s) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_admin, _pubkey, _ex, _ey, _vx, _vy, _s));
        return deployRemoteCall(Role_sol_Role.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Role_sol_Role> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, Address _admin, Utf8String _pubkey, Uint256 _ex, Uint256 _ey, Uint256 _vx, Uint256 _vy, Uint256 _s) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_admin, _pubkey, _ex, _ey, _vx, _vy, _s));
        return deployRemoteCall(Role_sol_Role.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
