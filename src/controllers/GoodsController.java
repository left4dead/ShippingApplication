package controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import model.Goods;
import service.GoodsServiceInMemory;

@RestController
@RequestMapping("/")
public class GoodsController {
    @Autowired
    GoodsServiceInMemory goodsService;

    @RequestMapping(value = "goods", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Goods> getAllGoods() {
        return goodsService.getAllGoods();
    }

    @RequestMapping(value = "goods/{id}", method = RequestMethod.GET)
    public @ResponseBody Goods getGoodsById(@PathVariable Long id,
                                            HttpServletRequest request,
                                            HttpServletResponse response) {
        Goods goods = null;

        try {
            goods = goodsService.getGoodsById(id);
        }
        catch (NullPointerException ex) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }

        return goods;
    }

    @RequestMapping(value = "goods", method = RequestMethod.POST)
    public @ResponseBody Long createGoods(@RequestBody Goods goods,
                                          HttpServletRequest request,
                                          HttpServletResponse response) {
        Long id = null;

        try {
            id = goodsService.createGoods(goods);
        }
        catch (NullPointerException ex) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }

        return id;
    }
}