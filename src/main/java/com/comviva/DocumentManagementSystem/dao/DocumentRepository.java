package com.comviva.DocumentManagementSystem.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.comviva.DocumentManagementSystem.bean.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {

	public List<Document> findAllByUserId(Integer userId);

	@Override
	public Document save(Document fileDB);

	public Document findByUserIdAndDocId(Integer userId,Integer docId);
}
