package com.segurosbolivar.telephonenumberservice.repository;

import com.segurosbolivar.telephonenumberservice.model.TelephoneNumberAudit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelephoneNumberAuditRepository extends CrudRepository<TelephoneNumberAudit, Long> {
}
