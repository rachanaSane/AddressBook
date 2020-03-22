package com.pwc.au.book.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pwc.au.book.model.Contact;



@Repository
public interface ContactRepository extends JpaRepository<Contact,Long> {
	

    Optional<Contact> findByIdAndAddressBookId(Long contactId, Long addressBookId);
    
    @Query("select c from Contact c inner join c.addressBook book where book.id = :addressBookId order by c.name")
    Collection<Contact> findByAddressBookId(@Param("addressBookId") Long addressBookId);       
  
    @Query("select c.name from Contact c inner join c.addressBook book where book.name IN ( :addressBookIdList) group by c.name having count(c.name) = 1 ")
    Collection<String> findUnCommonContacts(@Param("addressBookIdList") List<String> addressBookIdList);

}
