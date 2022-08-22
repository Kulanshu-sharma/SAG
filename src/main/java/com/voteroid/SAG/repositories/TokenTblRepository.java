package com.voteroid.SAG.repositories;

import org.springframework.data.repository.CrudRepository;

import com.voteroid.SAG.entities.TokenTbl;

public interface TokenTblRepository extends CrudRepository<TokenTbl,Integer> {

	public TokenTbl findByClientId(int clientId);
}
