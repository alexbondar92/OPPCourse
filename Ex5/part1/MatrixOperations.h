#include <stdio.h>

//#include "Utilities.h"
#include "Transpose.h"

template<typename R1, typename R2>
struct AddRow{
    static_assert(R1::size == R2::size, "Failed - not equal matrix size(AddRow template)");
    typedef typename PrependList<Int<R1::head::value + R2::head::value>, typename AddRow<typename R1::next,typename R2::next>::result>::list result;
};

template<>
struct AddRow<List<>,List<>>{
    typedef List<> result;
};

template<typename M1, typename M2>
struct Add{
    static_assert(M1::size == M2::size, "Failed - not equal matrix size(Add template)");
    typedef typename PrependList<typename AddRow<typename M1::head,typename M2::head>::result, typename Add<typename M1::next,typename M2::next>::result >::list result;
};

template<>
struct Add<List<>,List<>>{
    typedef List<> result;
};

template<typename R1, typename R2>
struct MultiplyRow{
    typedef Int<(R1::head::value) * (R2::head::value) + MultiplyRow<typename R1::next, typename R2::next>::value::value> value;
};

template<>
struct MultiplyRow<List<>,List<>>{
    typedef Int<0> value;
};

template<typename R1, typename M2>
struct MultiplyRows{
    typedef typename PrependList<typename MultiplyRow<R1,typename M2::head>::value, typename MultiplyRows<R1, typename M2::next>::row>::list row;
};

template<typename M>
struct MultiplyRows<M,List<>>{
    typedef List<> row;
};

template<typename M1, typename M2>
struct MultiplyAux{
    typedef typename PrependList<typename MultiplyRows<typename M1::head, M2>::row, typename MultiplyAux<typename M1::next, M2>::result>::list result;
};

template<typename M>
struct MultiplyAux<List<>,M>{
    typedef List<> result;
};

template<typename M1, typename M2>
struct Multiply{
    static_assert(M1::head::size == M2::size, "Failed - not equal matrix size(Multiply template)");
    typedef typename MultiplyAux<M1,typename Transpose<M2>::matrix>::result result;
};
