package com.voteroid.SAG.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.voteroid.SAG.entities.APITable;


public interface APITblRepository extends JpaRepository<APITable,Integer> {
	
     public APITable findByClientIdAndApiCall(int clientId,String apiCall);
     public List<APITable> findByClientId(int clientId);
     public List<APITable> findByClientIdAndBlocked(int clientId,boolean blocked);
}
