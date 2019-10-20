#ifndef OBSERVER_H
#define OBSERVER_H

#include <stdio.h>


template<typename T>
class Observer{
private:
    
    
public:
    Observer<T>(){
        
    }
    virtual void handleEvent(const T&) = 0;
    
    
};


#endif //OBSERVER_H
