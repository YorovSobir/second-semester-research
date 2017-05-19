#ifndef TEMP_NIR_GRAPH_H
#define TEMP_NIR_GRAPH_H

#include <glob.h>
#include <vector>
#include <iostream>
#include <random>

struct graph {
    graph(size_t n, size_t m)
            : n_(n)
            , m_(m)
            , in_adjlist(n)
            , out_adjlist(n)
            , betta(n)
            , weights(n, std::vector<double>(n))
    {
        for (int i = 0; i < m; ++i) {
            int u;
            int v;
            std::cin >> u >> v;
            in_adjlist[v - 1].push_back(u - 1);
            out_adjlist[u - 1].push_back(v - 1);
        }

    }

    void balancing_real_weights() {
        for (int i = 0; i < n_; ++i) {
            for (int j = 0; j < out_adjlist[i].size(); ++j) {
                weights[i][out_adjlist[i][j]] = 1.;
            }
        }

        std::random_device generator;
        std::uniform_real_distribution<double> distribution(0, 1);
        for (int i = 0; i < n_; ++i) {
            betta[i] = distribution(generator);
        }

        int k;
        for (k = 0; ; ++k) {
            bool is_changed = false;
            for (int i = 0; i < n_; ++i) {
                for (int j = 0; j < out_adjlist[i].size(); ++j) {
                    double in_sum = 0.;
                    for (int m = 0; m < in_adjlist[i].size(); ++m) {
                        in_sum += weights[in_adjlist[i][m]][i];
                    }
                    double temp = weights[i][out_adjlist[i][j]] +
                                                    betta[i] * (in_sum / out_adjlist[i].size() -
                                                               weights[i][out_adjlist[i][j]]);
                    if (fabs(temp - weights[i][out_adjlist[i][j]]) > epsilon) {
                        is_changed = true;
                    }
                    weights[i][out_adjlist[i][j]] = temp;
                }
            }
            if (!is_changed) {
                break;
            }
        }
        std::cout << "the number of iteration: " << k << std::endl;
    }

    void print_weights() {
        for (int i = 0; i < n_; ++i) {
            for (int j = 0; j < out_adjlist[i].size(); ++j) {
                std::cout << "(" << i + 1 << ", " << out_adjlist[i][j] + 1
                          << ") " << weights[i][out_adjlist[i][j]] << std::endl;
            }
        }
    }

private:
    size_t n_;
    size_t m_;
    std::vector<std::vector<int>> in_adjlist;
    std::vector<std::vector<int>> out_adjlist;
    std::vector<std::vector<double>> weights;
    std::vector<double> betta;
    constexpr static double epsilon = 1.e-5;
};
#endif //TEMP_NIR_GRAPH_H
