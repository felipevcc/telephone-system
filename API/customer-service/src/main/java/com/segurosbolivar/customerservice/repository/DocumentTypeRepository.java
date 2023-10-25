package com.segurosbolivar.customerservice.repository;

import com.segurosbolivar.customerservice.model.DocumentType;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentTypeRepository extends CrudRepository<DocumentType, Long> {
    @Query("SELECT DOCUMENT_TYPE_ID, TYPE_CODE, DESCRIPTION FROM DOCUMENT_TYPE")
    List<DocumentType> findAllDocumentTypes();
}
