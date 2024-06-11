import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/*
 * This class includes test cases for the basic/normal functionality of the 
 * FriendFinder.findClassmates method, but does not check for any error handling.
 */

public class FindClassmatesTest {

	protected FriendFinder ff;

	protected ClassesDataSource defaultClassesDataSource = new ClassesDataSource() {

		@Override
		public List<String> getClasses(String studentName) {

			if (studentName.equals("A")) {
				return List.of("1", "2", "3");
			} else if (studentName.equals("B")) {
				return List.of("1", "2", "3");
			} else if (studentName.equals("C")) {
				return List.of("2", "4");
			} else
				return null;

		}

	};

	protected StudentsDataSource defaultStudentsDataSource = new StudentsDataSource() {

		@Override
		public List<Student> getStudents(String className) {

			Student a = new Student("A", 101);
			Student b = new Student("B", 102);
			Student c = new Student("C", 103);

			if (className.equals("1")) {
				return List.of(a, b);
			} else if (className.equals("2")) {
				return List.of(a, b, c);
			} else if (className.equals("3")) {
				return List.of(a, b);
			} else if (className.equals("4")) {
				return List.of(c);
			} else
				return null;
		}

	};

	@Test
	public void testFindOneFriend() {

		ff = new FriendFinder(defaultClassesDataSource, defaultStudentsDataSource);
		Set<String> response = ff.findClassmates(new Student("A", 101));
		assertNotNull(response);
		assertEquals(1, response.size());
		assertTrue(response.contains("B"));

	}

	@Test
	public void testFindNoFriends() {

		ff = new FriendFinder(defaultClassesDataSource, defaultStudentsDataSource);
		Set<String> response = ff.findClassmates(new Student("C", 103));
		assertNotNull(response);
		assertTrue(response.isEmpty());

	}

	@Test
	public void testClassesDataSourceReturnsNullForInputStudent() {

		ff = new FriendFinder(defaultClassesDataSource, defaultStudentsDataSource);
		Set<String> response = ff.findClassmates(new Student("D", 104));
		assertNotNull(response);
		assertTrue(response.isEmpty());

	}

	/**************************************************
	 * Tests for null, addition to default
	 **************************************************/

	protected ClassesDataSource simpleCDS1, simpleCDS2, simpleCDS3, simpleCDS4;
	protected StudentsDataSource simpleSDS1, simpleSDS2, simpleSDS3, simpleSDS4;

	@Before
	public void setUp() {
		setSimpleDataSource1();
		setSimpleDataSource2();
		setSimpleDataSource3();
		setSimpleDataSource4();
	}

	public void setSimpleDataSource1() {
		simpleCDS1 = new ClassesDataSource() {

			@Override
			public List<String> getClasses(String studentName) {

				if (studentName.equals("A")) {
					return List.of(
							"HIST",
							"ECON",
							"MATH",
							"PHYS",
							"",
							null);
				} else if (studentName.equals("B")) {
					return List.of(
							"ECON",
							"MATH");
				} else if (studentName.equals("C")) {
					return List.of(
							"MATH",
							"PHYS");
				} else if (studentName.equals("D")) {
					return List.of(
							"PHYS",
							"HIST");
				} else if (studentName.equals(null)) {
					return List.of(
							"HIST",
							"ECON",
							"MATH",
							"PHYS",
							"");
				} else if (studentName.equals("")) {
					return List.of(
							"HIST",
							"ECON",
							"MATH",
							"PHYS",
							"");
				} else {
					return null;
				}
			}

		};

		simpleSDS1 = new StudentsDataSource() {
			Student studentA = new Student("A", 0);
			Student studentB = new Student("B", 1);
			Student studentC = new Student("C", 2);
			Student studentD = new Student("D", 3);
			Student studentNullName = new Student(null, 5);
			Student studentEmptyName = new Student("", 6);

			@Override
			public List<Student> getStudents(String className) {
				if (className.equals("HIST")) {
					return List.of(
							studentA,
							studentD,
							studentNullName,
							studentEmptyName);
				} else if (className.equals("ECON")) {
					return List.of(
							studentA,
							studentB,
							studentNullName,
							studentEmptyName);
				} else if (className.equals("MATH")) {
					return List.of(
							studentA,
							studentB,
							studentC,
							studentNullName,
							studentEmptyName);
				} else if (className.equals("PHYS")) {
					return null;
				} else if (className.equals("")) {
					return List.of(
							studentA,
							studentB,
							studentC,
							studentD,
							studentNullName,
							studentEmptyName);
				} else if (className.equals(null)) {
					return List.of(
							studentA,
							studentB,
							studentC,
							studentD,
							studentNullName,
							studentEmptyName);
				} else if (className.equals("BIOL")) {
					return List.of();
				} else {
					return null;
				}
			}
		};
	}

	public void setSimpleDataSource2() {
		simpleCDS2 = new ClassesDataSource() {

			@Override
			public List<String> getClasses(String studentName) {

				if (studentName.equals("A")) {
					return List.of(
							"HIST",
							"ECON");
				} else if (studentName.equals("B")) {
					return List.of(
							"HIST",
							"ECON",
							"MATH");
				} else if (studentName.equals("")) {
					return List.of(
							"HIST",
							"ECON",
							"MATH");
				} else if (studentName.equals(null)) {
					return List.of(
							"HIST",
							"ECON",
							"MATH");
				} else {
					return null;
				}
			}

		};

		simpleSDS2 = new StudentsDataSource() {
			Student studentA = new Student("A", 0);
			Student studentB = new Student("B", 1);
			Student studentUnnamed = new Student("", 2);
			Student studentNull = new Student(null, 3);

			@Override
			public List<Student> getStudents(String className) {
				if (className.equals("HIST")) {
					return List.of(
							studentA,
							studentB,
							studentUnnamed,
							studentNull);
				} else if (className.equals("ECON")) {
					return List.of(
							studentA,
							studentB,
							studentUnnamed,
							studentNull);
				} else if (className.equals("MATH")) {
					return List.of(
							studentB,
							studentUnnamed,
							studentNull);
				} else if (className.equals("BIOL")) {
					return List.of();
				} else {
					return null;
				}
			}

		};

	}

	public void setSimpleDataSource3() {
		simpleCDS3 = new ClassesDataSource() {

			@Override
			public List<String> getClasses(String studentName) {

				if (studentName.equals("A")) {
					return List.of(
							"HIST",
							"ECON",
							"MATH",
							"PHYS",
							"");
				} else if (studentName.equals("B")) {
					return List.of(
							"ECON",
							"MATH");
				} else if (studentName.equals("C")) {
					return List.of(
							"MATH",
							"PHYS");
				} else if (studentName.equals("D")) {
					return List.of(
							"PHYS",
							"HIST");
				} else if (studentName.equals(null)) {
					return List.of(
							"HIST",
							"ECON",
							"MATH",
							"PHYS",
							"");
				} else if (studentName.equals("")) {
					return List.of(
							"HIST",
							"ECON",
							"MATH",
							"PHYS",
							"");
				} else {
					return null;
				}
			}

		};

		simpleSDS3 = new StudentsDataSource() {
			Student studentA = new Student("A", 0);
			Student studentB = new Student("B", 1);
			Student studentC = new Student("C", 2);
			Student studentD = new Student("D", 3);
			Student studentNull = null;
			Student studentNullName = new Student(null, 5);
			Student studentEmptyName = new Student("", 6);

			@Override
			public List<Student> getStudents(String className) {
				if (className.equals("HIST")) {
					return List.of(
							studentA,
							studentD,
							studentNullName,
							studentEmptyName,
							studentNull);
				} else if (className.equals("ECON")) {
					return List.of(
							studentA,
							studentB,
							studentNullName,
							studentEmptyName);
				} else if (className.equals("MATH")) {
					return List.of(
							studentA,
							studentB,
							studentC,
							studentNullName,
							studentEmptyName);
				} else if (className.equals("PHYS")) {
					return null;
				} else if (className.equals("")) {
					return List.of(
							studentA,
							studentB,
							studentC,
							studentD,
							studentNullName,
							studentEmptyName);
				} else if (className.equals(null)) {
					return List.of(
							studentA,
							studentB,
							studentC,
							studentD,
							studentNullName,
							studentEmptyName);
				} else if (className.equals("BIOL")) {
					return List.of();
				} else {
					return null;
				}
			}
		};
	}

	public void setSimpleDataSource4() {
		simpleCDS4 = new ClassesDataSource() {

			@Override
			public List<String> getClasses(String studentName) {

				if (studentName.equals("A")) {
					return List.of(
							"HIST",
							"ECON",
							"MATH",
							"PHYS",
							"");
				} else if (studentName.equals("B")) {
					return List.of(
							"ECON",
							"MATH");
				} else if (studentName.equals("C")) {
					return List.of(
							"MATH",
							"PHYS");
				} else if (studentName.equals("D")) {
					return List.of(
							"PHYS",
							"HIST");
				} else if (studentName.equals(null)) {
					return List.of(
							"HIST",
							"ECON",
							"MATH",
							"PHYS",
							"");
				} else if (studentName.equals("")) {
					return List.of(
							"HIST",
							"ECON",
							"MATH",
							"PHYS",
							"");
				} else {
					return null;
				}
			}

		};

		simpleSDS4 = new StudentsDataSource() {
			Student studentA = new Student("A", 0);
			Student studentB = new Student("B", 1);
			Student studentC = new Student("C", 2);
			Student studentD = new Student("D", 3);
			Student studentNullName = new Student(null, 5);
			Student studentEmptyName = new Student("", 6);

			@Override
			public List<Student> getStudents(String className) {
				if (className.equals("HIST")) {
					return List.of(
							studentA,
							studentD,
							studentNullName,
							studentEmptyName);
				} else if (className.equals("ECON")) {
					return List.of(
							studentA,
							studentB,
							studentNullName,
							studentEmptyName);
				} else if (className.equals("MATH")) {
					return List.of(
							studentA,
							studentB,
							studentC,
							studentNullName,
							studentEmptyName);
				} else if (className.equals("PHYS")) {
					return null;
				} else if (className.equals("")) {
					return List.of(
							studentA,
							studentB,
							studentC,
							studentD,
							studentNullName,
							studentEmptyName);
				} else if (className.equals(null)) {
					return List.of(
							studentA,
							studentB,
							studentC,
							studentD,
							studentNullName,
							studentEmptyName);
				} else if (className.equals("BIOL")) {
					return List.of();
				} else {
					return null;
				}
			}
		};
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSourcesNull() {
		ff = new FriendFinder(null, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCDSIsNull() {
		ff = new FriendFinder(null, defaultStudentsDataSource);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSDSIsNull() {
		ff = new FriendFinder(defaultClassesDataSource, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInputStudentIsNull() {
		ff = new FriendFinder(defaultClassesDataSource, defaultStudentsDataSource);
		ff.findClassmates(null);

	}

	@Test(expected = IllegalStateException.class)
	public void testInputStudentNameIsNull() {
		ff = new FriendFinder(defaultClassesDataSource, defaultStudentsDataSource);
		ff.findClassmates(new Student(null, 0));

	}

	@Test(expected = IllegalStateException.class)
	public void testInputStudentNameIsEmpty() {
		ff = new FriendFinder(defaultClassesDataSource, defaultStudentsDataSource);
		ff.findClassmates(new Student("", 0));

	}

	@Test(expected = IllegalStateException.class)
	public void testSimpleNullCourseNameSearch() {
		ff = new FriendFinder(simpleCDS1, simpleSDS1);
		ff.findClassmates(new Student("A", 0));
	}

	@Test(expected = IllegalStateException.class)
	public void testSimpleNullStudentSearch() {
		ff = new FriendFinder(simpleCDS3, simpleSDS3);
		ff.findClassmates(new Student("A", 0));
	}

	@Test
	public void testSimpleInvalidStudentNameSearch() {
		ff = new FriendFinder(simpleCDS2, simpleSDS2);
		Set<String> response = ff.findClassmates(new Student("A", 0));

		// result
		assertEquals(1, response.size());
		assertTrue(response.contains("B"));
	}

	@Test
	public void testSimpleInvalidCourseNameSearch() {
		ff = new FriendFinder(simpleCDS4, simpleSDS4);
		Set<String> response = ff.findClassmates(new Student("A", 0));

		// result
		assertEquals(0, response.size());
	}
}
