#ifndef PART2_OOP5EVENTEXCEPTION_H
#define PART2_OOP5EVENTEXCEPTION_H

#include <exception>

class BaseException : public std::exception {};

class ObserverUnknownToSubject : public BaseException {};

class ObserverAlreadyKnownToSubject : public BaseException {};





#endif //PART2_OOP5EVENTEXCEPTION_H
