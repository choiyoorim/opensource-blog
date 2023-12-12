package com.opensource.blog.repository;

import com.opensource.blog.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long>
{

}
//MemberRepository.interface