package com.loankim.examonline.dao;

/**
 * @author LamHM
 *
 */
public interface SequenceRepository {
	long getNextSequenceId(String key);


	void createSequenceDocument(String documentName, long defaultValue);
}
