package com.noCountry.uala.security.entity.mapper;

import com.noCountry.uala.security.dto.UserResponseDto;
import com.noCountry.uala.security.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
	public UserResponseDto EntityToDto(Usuario usuario){
		UserResponseDto responseDto = new UserResponseDto();
        responseDto.setIdUser(usuario.getIdUsuario());
		responseDto.setName(usuario.getNombre());
		responseDto.setNameUser(usuario.getNombreUsuario());
        responseDto.setEmail(usuario.getEmail());
		responseDto.setBalance(usuario.getWallet().totalList());
		responseDto.setCBU(usuario.getWallet().getCbu());
		return responseDto;
	}

}
