package br.com.bratatouille.management.generated.api;

import br.com.bratatouille.management.generated.model.AdjustStockRequest;
import br.com.bratatouille.management.generated.model.StockAlertResponse;
import br.com.bratatouille.management.generated.model.StockMovementResponse;
import br.com.bratatouille.management.generated.model.StockResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-08-06T03:02:01.523540700-03:00[America/Sao_Paulo]", comments = "Generator version: 7.14.0")
@Controller
@RequestMapping("${openapi.bratatouilleManagement.base-path:}")
public class StocksApiController implements StocksApi {

    private final StocksApiDelegate delegate;

    public StocksApiController(@Autowired(required = false) StocksApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new StocksApiDelegate() {});
    }

    @Override
    public StocksApiDelegate getDelegate() {
        return delegate;
    }

}
