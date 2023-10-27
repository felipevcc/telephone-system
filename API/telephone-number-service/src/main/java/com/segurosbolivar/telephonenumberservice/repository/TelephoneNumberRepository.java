package com.segurosbolivar.telephonenumberservice.repository;

import com.segurosbolivar.telephonenumberservice.model.TelephoneNumber;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelephoneNumberRepository extends CrudRepository<TelephoneNumber, Long> {
}
