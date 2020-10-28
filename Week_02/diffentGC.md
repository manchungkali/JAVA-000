# 不同GC的小结
## 总论
1. 同等内存分配的情况下，G1 GC共生成对象次数4026次；排在第二位的为并行GC，3889次；第三GMS GC，3221次；第四串行GC，1069次。 G1 GC的执行效率高。
2. 分配512M 的内存，YoungGC失败(Allocation Failure)，FullGC基本没有显示。并行GC没有设置Xms，则发生Full GC(原因为Ergonomics)
3.不管Full GC还是Young GC，都是将年轻代的占比转移至老年代，让出空间给有价值的运行事件。