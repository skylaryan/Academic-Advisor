package edu.wisc.academicadvisor.repositories;

import edu.wisc.academicadvisor.model.Course;

import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, String> {

}
