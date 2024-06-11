import java.util.List;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class FriendFinder {

	protected ClassesDataSource classesDataSource;
	protected StudentsDataSource studentsDataSource;

	public FriendFinder(ClassesDataSource cds, StudentsDataSource sds) {
		if (cds == null || sds == null) {
			throw new IllegalArgumentException("Data sources cannot be null");
		}
		classesDataSource = cds;
		studentsDataSource = sds;
	}

	/*
	 * This method takes a String representing the name of a student
	 * and then returns a set containing the names of everyone else
	 * who is taking the same classes as that student.
	 */
	public Set<String> findClassmates(Student theStudent) {
		if (theStudent == null) {
			throw new IllegalArgumentException("Student cannot be null");
		}

		String name = theStudent.getName();
		if (name == null || name.isEmpty()) {
			throw new IllegalStateException("Student name cannot be null or empty");
		}

		try {
			// find the classes that this student is taking
			List<String> myClasses = classesDataSource.getClasses(name);

			if (myClasses == null) {
				return Collections.emptySet(); // return empty Set if the student isn't taking any classes
			}

			// use the classes to find the names of the students
			Set<String> classmates = new HashSet<String>();

			for (String myClass : myClasses) {
				if (myClass.isEmpty()) {
					continue;
				}

				try {
					// list all the students in the class
					List<Student> students = studentsDataSource.getStudents(myClass);

					if (students == null) {
						continue; // skip this class if no students are found
					}

					for (Student otherStudent : students) {
						if (otherStudent.getName() == null || otherStudent.getName().isEmpty()) {
							continue; // skip null students
						}

						// find the other classes that they're taking
						List<String> theirClasses = classesDataSource.getClasses(otherStudent.getName());

						// see if all of the classes that they're taking are the same as the ones this
						// student is taking
						boolean allSame = true;
						for (String c : myClasses) {
							if (theirClasses.contains(c) == false) {
								allSame = false;
								break;
							}
						}

						// if they're taking all the same classes, then add to the set of classmates
						if (allSame) {
							if (otherStudent.getName().equals(name) == false) {
								classmates.add(otherStudent.getName());
							}
						}
					}
				} catch (NullPointerException e) {
					throw new IllegalStateException("students cannot contain null");
				}

			}

			return classmates;
		} catch (NullPointerException e) {
			throw new IllegalStateException("classes cannot contain null");
		}
	}
}