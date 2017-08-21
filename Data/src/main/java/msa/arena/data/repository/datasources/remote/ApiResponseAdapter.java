package msa.arena.data.repository.datasources.remote;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;

/**
 * Created by Abhimuktheeswarar on 15-06-2017.
 */

public class ApiResponseAdapter<R> implements CallAdapter<R, ApiResponse<R>> {
    private final Type responseType;

    public ApiResponseAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public ApiResponse<R> adapt(Call<R> call) {
        return null;
    }
}
