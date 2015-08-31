package planet.dao;

import planet.ConnectionFactory;
import planet.entity.FinReport;
import planet.entity.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

/**
 * Created by oleksii on 20.08.15.
 */
public class FinReportDaoImpl {
    public List<FinReport> select(Date begDate, Date endDate){
        List <FinReport> reports = new ArrayList<FinReport>();
        try (Connection connection = ConnectionFactory.getConnection();
        ) {
            try {
                String sqlGood =
                        "Select o.order_date, u.login, p.name, o.product_qty, o.amount from orders o, product p, user u where o.product_id = p.id and o.user_id = u.id and DATE(o.order_date) between ? and ?";
                PreparedStatement statement = connection.prepareStatement(sqlGood);
                statement.setDate(1, begDate);
                statement.setDate(2, endDate);


                statement.toString();
                ResultSet rs= statement.executeQuery();

                while(rs.next())
                {
                    FinReport r = new FinReport();
                    r.setLogin(rs.getString("login"));
                    r.setProductQty(rs.getInt("product_qty"));
                    r.setAmount(rs.getDouble("amount"));
                    r.setOrderDate(rs.getTimestamp("order_date"));
                    r.setProductName(rs.getString("name"));

                    reports.add(r);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reports;
    }
}
