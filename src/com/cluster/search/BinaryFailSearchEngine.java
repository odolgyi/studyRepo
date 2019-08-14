package com.cluster.search;

import com.cluster.server.isFaillable;

public class BinaryFailSearchEngine {

    public Result binarySearchServer(isFaillable currentFallibleWithInners) {
        int begin = 0;
        int end = currentFallibleWithInners.getSize() - 1;
        while (end - begin > 1) {
            int current = (begin + end) / 2;
            if (!currentFallibleWithInners.getInnerFallible(current).isFailed()) {
                end = current;
            } else {
                begin = current;
            }
        }
        return getResult(currentFallibleWithInners, begin, end);
    }

    private Result getResult(isFaillable currentFallibleWithInners, int start, int end) {
        isFaillable innerFailed = currentFallibleWithInners.getInnerFallible(start);
        if (innerFailed.isFailed()) {
            innerFailed = currentFallibleWithInners.getInnerFallible(end);
        }
        return getResult(innerFailed);
    }

    private Result getResult(isFaillable failed) {
        if (failed.getSize() > 0) {
            Result result = binarySearchServer(failed);
            result.setFailedServer(failed.getId());
            return result;
        } else {
            return new Result(failed.getId());
        }
    }
}
