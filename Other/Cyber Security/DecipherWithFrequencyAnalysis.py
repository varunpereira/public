from string import ascii_letters
cipher_letters = 'borjaqnfg??hpdct?ikes?l?m?BORJAQNFG??HPDCT?IKES?L?M?'
#corresponds to: ‘abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ’
def decipher(text):
 trans = str.maketrans(cipher_letters, ascii_letters)
 return text.translate(trans)
if __name__ == '__main__':
 text_to_cipher = input('Text to decipher: ')
 ciphered = decipher(text_to_cipher)
print(f'Deciphered text: {ciphered}')