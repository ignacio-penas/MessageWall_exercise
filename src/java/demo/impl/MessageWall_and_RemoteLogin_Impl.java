package demo.impl;

import demo.spec.Message;
import demo.spec.MessageWall;
import demo.spec.RemoteLogin;
import demo.spec.UserAccess;
import java.util.ArrayList;
import java.util.List;

public class MessageWall_and_RemoteLogin_Impl implements RemoteLogin, MessageWall {

    private List<Message> messages;
    
    public MessageWall_and_RemoteLogin_Impl(){
        messages = new ArrayList<Message>();
    }
    @Override
    public UserAccess connect(String usr, String passwd) {
        //pass access function should be called here
        UserAccess newUser = new UserAccess_Impl(this, usr);
        return newUser;
    }

    @Override
    public void put(String user, String msg) {
        Message inputMsg = new Message_Impl(user,msg);
        this.messages.add(inputMsg);
    }

    @Override
    public boolean delete(String user, int index) {
        if(this.messages.size() > index){
            if(this.messages.get(index).getOwner().equals(user))
            {
                this.messages.remove(index);
                return true;
            }
            else
            {
                return false;
                
            }
        }
        else{
            //throw new IndexOutOfBoundsException("Index higher than size");
            return false;
        }
    }

    @Override
    public Message getLast() {
        if (!this.messages.isEmpty()){
            return this.messages.get(this.messages.size()-1);
        }
        else
        {
            return null;
        }
    }

    @Override
    public int getNumber() {
        return this.messages.size();
    }

    @Override
    public List<Message> getAllMessages() {
        return this.messages;
    }

    
}
