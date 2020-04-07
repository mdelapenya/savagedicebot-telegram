package telegrambots.core;

public class TelegramUser
{
    private Long id;
    private String firstName;
    private String lastName;
    private String alias;

    public TelegramUser()
    {
        this(null, null, null, null);
    }

    public TelegramUser(Long id, String firstName, String lastName, String alias)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.alias = alias;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getAlias()
    {
        return alias;
    }

    public void setAlias(String alias)
    {
        this.alias = alias;
    }

    @Override
    public String toString()
    {
        return "TelegramUser{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", alias=" + alias + "}";
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 71 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        
        final TelegramUser other = (TelegramUser) obj;
        
        if (this.id != other.id)
        {
            return false;
        }
        
        return true;
    }
}