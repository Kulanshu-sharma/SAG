package com.voteroid.SAG.configurations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class GeneralOperations {

	public Set<Integer> fetchSetOfApisFromList(List<Integer> apiIds) {
		Set<Integer> set = new HashSet<Integer>();
		apiIds.forEach(data -> set.add(data));
		return set;
	}

	
}
