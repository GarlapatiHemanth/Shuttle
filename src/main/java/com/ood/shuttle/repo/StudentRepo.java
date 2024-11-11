package com.ood.shuttle.repo;

import com.ood.shuttle.entity.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepo extends CrudRepository<Student, Long> {

    Student findBySuid(long suid);


}
