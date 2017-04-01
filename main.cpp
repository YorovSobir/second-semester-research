#include <iostream>
#include "graph.h"

int main() {
    size_t n;
    size_t m;
    std::cout << "input n and m:\n";
    std::cin >> n >> m;
    graph g(n, m);
    g.balancing_real_weights();
    g.print_weights();
    return 0;
}