#include <stdio.h>
#include "Observer.h"
#include "OOP5EventException.h"
#include <set>

using namespace std;

template<typename T>
class Subject {
private:
    set<Observer<T>*> observers;
    
    
public:
    Subject<T>(){

    }
    
    void notify (const T& msg){
        for (Observer<T>* ob : observers){
            ob->handleEvent(msg);
        }
    }
    
    void addObserver (Observer<T>& ob){
        if (this->observers.end() == this->observers.find(&ob)){
            this->observers.insert(&ob);
        } else {
            throw ObserverAlreadyKnownToSubject();
        }
    }
    
    void removeObserver (Observer<T>& ob){
        if (this->observers.end() != this->observers.find(&ob)){
            this->observers.erase(&ob);
        } else {
            throw ObserverUnknownToSubject();
        }
    }
    
    Subject<T>& operator+=(Observer<T>& ob){
        this->addObserver(ob);
        return *this;
    }
    
    Subject<T>& operator-=(Observer<T>& ob){
        this->removeObserver(ob);
        return *this;
    }
    
    Subject<T>& operator()(const T& msg){
        this->notify(msg);
        return *this;
    }
};
