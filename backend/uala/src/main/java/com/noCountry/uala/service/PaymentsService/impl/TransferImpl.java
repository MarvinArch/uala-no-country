package com.noCountry.uala.service.PaymentsService.impl;
import com.noCountry.uala.models.dto.request.TranferMethodDto;
import com.noCountry.uala.models.dto.request.UserCbuOrAliasRequestDto;
import com.noCountry.uala.models.entity.Wallet;
import com.noCountry.uala.models.entity.payamentsMethod.Transfer;
import com.noCountry.uala.repository.PaymentsRepository.TransferRepository;
import com.noCountry.uala.repository.WalletRepository;
import com.noCountry.uala.security.dto.UserResponseDto;
import com.noCountry.uala.security.entity.Usuario;
import com.noCountry.uala.security.entity.mapper.UserMapper;
import com.noCountry.uala.security.repository.UsuarioRepository;
import com.noCountry.uala.security.service.UsuarioService;
import com.noCountry.uala.security.util.GetUserLogged;
import com.noCountry.uala.service.PaymentsService.IPayments;
import com.noCountry.uala.service.PaymentsService.ITransferToThirdParties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import static com.noCountry.uala.enums.PaymentMethods.PAGO_FACIL;

@Service
@RequiredArgsConstructor
public class TransferImpl implements IPayments, ITransferToThirdParties {
	private final TransferRepository transferRepository;
	private final GetUserLogged getUserLogged;
	private final UsuarioRepository usuarioRepository;
	private final UserMapper userMapper;
	private final WalletRepository walletRepository;
	private final UsuarioService usuarioService;

	@Override
	public boolean registerPayment(double cash) {
		Transfer transfer= new Transfer();
		if (transfer.calculatePayments(cash))
		{
			transfer.setReferenceNumber(numberOfReference());
			transfer.setCashAmount(cash);
			transfer.setWallet(getUserLogged.walletOfSession());
			transfer.setEntity(PAGO_FACIL.toString());
			transferRepository.save(transfer);
			return true;
		}
		return false;
	}
	public int numberOfReference(){
		return (int) (Math.random()*90000+30000);
	}



//Transferencia

	@Override
	public boolean sendTransfer(TranferMethodDto dato) {

		Wallet wallet1 = walletRepository.findByCbu(((Long) dato.getValor()).longValue());
		Wallet wallet = getUserLogged.walletOfSession();
		if (wallet.getBalance()> dato.getCashAmount())
		{
			wallet.setBalance(wallet.getBalance() - dato.getCashAmount());
			walletRepository.save(wallet);
			wallet1.setBalance( dato.getCashAmount() + wallet1.getBalance());
			walletRepository.save(wallet1);
		return true;
		}
		return false;
	}

	@Override
	public UserResponseDto findToCbuOrAlias(UserCbuOrAliasRequestDto dato) {
		if (  dato.getValor() instanceof  String) {
			Wallet wallet1 = walletRepository.findByAlias(dato.getValor().toString());
			Usuario usuario = usuarioRepository.findById(wallet1.getId().intValue()).orElseThrow();
			UserResponseDto responseDto = userMapper.EntityToDto(usuario);
			System.out.println(responseDto);
			return responseDto;
		}
		else{
			System.out.println(dato.getValor() );
			Wallet wallet = walletRepository.findByCbu(((Long) dato.getValor()).longValue());
			Usuario usuario1 = usuarioRepository.findById(wallet.getId().intValue()).orElseThrow();
			UserResponseDto responseDto1 = userMapper.EntityToDto(usuario1);
			return responseDto1;
		}
	}
}
