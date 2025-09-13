#!/bin/bash
# will differ based on OS.
echo "--- Stopping all food-delivery-platform services ---"

pkill -f api-gateway
pkill -f order-service
pkill -f customer-service
pkill -f restaurant-service
pkill -f delivery-service

echo "--- All services stopped. ---"