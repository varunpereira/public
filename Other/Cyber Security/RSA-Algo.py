import math
n = 10772542097
total_num_comp = 10
your_comp_num = 10
if total_num_comp == 1:
 min = 2
 max = math.sqrt(n)
elif total_num_comp > 1:
 max = (math.sqrt(n) / 10) * your_comp_num
 if your_comp_num == 1:
 min = 2
 else:
 min = math.floor(math.sqrt(n) / 10) * (your_comp_num - 1)
a = n
while min <= max:
 if a % min:
 min += 1
 else:
 a //= min
q = int(n/a)
phi_n = (a-1)*(q-1)
print("p is " + str(a))
print("q is " + str(q))
print("phi(n) is " + str(phi_n))
