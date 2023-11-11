// Using the CPUID opcode in X86 to get the CPU model name

#include <stdio.h>

int main() {
    unsigned int eax, ebx, ecx, edx;
    char brand[48];
    for (int i = 0; i < 3; i++) {
        eax = 0x80000002 + i;
        __asm__ volatile("cpuid"
            : "=a" (eax),
              "=b" (ebx),
              "=c" (ecx),
              "=d" (edx)
            : "a" (eax)
        );
        unsigned int offset = 16 * i;
        *(unsigned int*)(brand + offset) = eax;
        *(unsigned int*)(brand + offset + 4) = ebx;
        *(unsigned int*)(brand + offset + 8) = ecx;
        *(unsigned int*)(brand + offset + 12) = edx;
    }
    printf("CPU Model: %s\n", brand);
    return 0;
}
