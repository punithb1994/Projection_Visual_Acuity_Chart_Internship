package eyecare.visint.pac.pac;

/*
 *********Created by Akshath K, Punith B, Ajay J*********
*/
public class Flag
{
    static int flag;
    static String pid;

    public void setflag(int i)
    {
        flag = i;
    }

    public void setpid(String j)
    {
        pid = j;
    }

    public int returnflag()
    {
        return flag;
    }

    public String returnpid()
    {
        return pid;
    }
}