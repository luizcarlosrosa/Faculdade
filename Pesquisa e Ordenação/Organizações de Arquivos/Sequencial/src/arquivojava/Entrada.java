package arquivojava;


import java.io.BufferedReader;
import java.io.InputStreamReader;

//... classe Entrada (possui métodos para entrada de dados) ....
class Entrada
{
    public static String leString(String msg)
    {
        String line="";
        InputStreamReader isr= new InputStreamReader(System.in);   //system.in eh o console(teclado )
        BufferedReader br= new BufferedReader(isr);
        try
        {
        System.out.println(msg);
        line = br.readLine();
        }
        catch(Exception e){}
        return line;
    }

    public static int leInteger(String msg)
    {
        String line="";
        InputStreamReader isr= new InputStreamReader(System.in);
        BufferedReader br= new BufferedReader(isr);
        try
        {
            System.out.println(msg);
            line=br.readLine();
            int retorno=Integer.valueOf(line).intValue();
            return retorno;
        }
        catch(Exception e)
        {
            return -1;
        }
    }
}
