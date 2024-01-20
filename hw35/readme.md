add, remove, firstentry, lastentry в TreeMap - O(log(N))
size, O(1)

Итого в худшем все операции MedianList кроме size - O(log(N)), size O(1)

Можно и getMedian довести до O(1), если прикапывать в NonUniqueTreeMap min и max после каждого изменения, но мне чота лень. :-)