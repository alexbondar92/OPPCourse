#include <stdio.h>

template<int N>
struct Int{
    static constexpr int value = N;
};

template<typename... TT>
struct List{};

template<typename T, typename... TT>
struct List<T,TT...>{
    typedef T head;
    typedef List<TT...> next;
    static constexpr int size = sizeof...(TT)+1;
};

template<>
struct List<>{
    static constexpr int size = 0;
};

template<typename T,typename TT>
struct PrependList{};

template<typename T, typename... TT>
struct PrependList<T,List<TT...>>{
    typedef List<T,TT...> list;
};


template<int N, typename T>
struct ListGet{
    typedef typename ListGet<N-1, typename T::next>::value value;
};

template<typename T>
struct ListGet<0, T>{
    typedef typename T::head value;
};


template<int N, typename U, typename T>
struct ListSet{
    typedef typename PrependList<typename T::head, typename ListSet<N-1, U, typename T::next>::list>::list list;
};

template<typename U, typename T>
struct ListSet<0, U, T>{
    typedef typename PrependList<U, typename T::next>::list list;
};


