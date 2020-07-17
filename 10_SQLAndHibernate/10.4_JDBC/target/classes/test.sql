SELECT course_name, COUNT(MONTH(subscription_date)) / COUNT(DISTINCT MONTH(subscription_date)) AS average_purchases_count
FROM PurchaseList
GROUP BY course_name;