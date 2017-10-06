package teammint.classroster;

/**
 * Created by Sebmansigh on 10/4/2017.
 */

public class Student
{
    private String Name;
    private String Major;

    public Student(int i)
    {
        Name = "Student"+i;
        Major = "Major"+(i%3);
    }

    public String getName()
    {
        return Name;
    }
    public String getMajor()
    {
        return Major;
    }
}
