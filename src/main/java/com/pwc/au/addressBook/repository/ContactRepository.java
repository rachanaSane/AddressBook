package com.pwc.au.addressBook.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.pwc.au.addressBook.model.Contact;


@Repository
public interface ContactRepository extends JpaRepository<Contact,Long> {
	
	//List<Contact> findByAddressBookId(Long addressBookId);
    Optional<Contact> findByIdAndAddressBookId(Long id, Long addressBookId);
    
    @Query("select c from Contact c inner join c.addressBook book where book.id = :addressBookId order by c.name")
    Collection<Contact> findByAddressBookId(@Param("addressBookId") Long addressBookId);
    
    
  //  @Query("select c.name from Contact c inner join c.addressBook book where book.id IN ( :addressBookId_1 , :addressBookId_2) group by c.name having count(c.name) > 1 ")
   // Collection<String> findUnCommonRecords(@Param("addressBookId_1") Long addressBookId1, @Param("addressBookId_2") Long addressBookId2);
	
    
    @Query("select c.name from Contact c inner join c.addressBook book where book.id IN ( :addressBookIdList) group by c.name having count(c.name) = 1 ")
    Collection<String> findUnCommonRecords(@Param("addressBookIdList") List<Long> addressBookIdList);

}
