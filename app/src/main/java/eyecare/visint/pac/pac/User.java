package eyecare.visint.pac.pac;

/*
 *********Created by Akshath K, Punith B, Ajay J*********
*/
public class User
{
    public String id, fname, lname, gender, dob, mobile, address, consultant;

    public User(String id, String fname, String lname, String gender, String dob, String mobile, String address, String consultant)
    {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.gender = gender;
        this.dob = dob;
        this.mobile = mobile;
        this.address = address;
        this.consultant = consultant;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getFname()
    {
        return fname;
    }

    public String getLname()
    {
        return lname;
    }

    public String getGender()
    {
        return gender;
    }

    public String getDob()
    {
        return dob;
    }

    public String getMobile()
    {
        return mobile;
    }

    public String getAddress()
    {
        return address;
    }

    public String getConsultant()
    {
        return consultant;
    }

}