package com.voteroid.SAG.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.voteroid.SAG.entities.APITable;


public interface APITblRepository extends JpaRepository<APITable,Integer> {
           APITable findByClientIdAndApiCall(int clientId,String apiCall);
}
