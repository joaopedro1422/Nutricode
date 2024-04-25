# Nutricode
  Vídeo de demonstração: https://youtu.be/O1FWJCFl-rg
  
  Aplicativo desktop Full stack, construído a partir das linguagen:s Java (JavaFx para interface gráfica interativa), Sql, python e css. Se trata de um sistema de gerenciamento de nutrição individual, o qual permite 
consultas à tabela nutricional de diversos alimentos(Com dados sólidos a partir da Api da USDA), criaçao e manuseio de refeições utilizando os alimentos do banco de dados e gerenciamento
da rotina nutricional do usuário. Além disso, fornece os dados totais (carboidratos,Proteínas,Calorias...) de cada refeição, de cada dia e a média diária de ingestão dos nutrientes 
necessários por dia, durante a semana. O nutricode possui uma interface dinâmica, intuitiva e esteticamente impecável e se apresenta como uma ideia interessante para aqueles que visam
uma melhor alimentação, gerando dados concretos e comparando-os com as recomendações de médicos e nutricionistas. 
  Os dados do aplicativo são armazenados em um Banco de dados e manuseados a partir de consultas Sql. A linguagem python foi utilizada para a automatização da inserção dos alimentos e suas 
informações a partir da Api da USDA (United States Department of Agriculture) e para a automatização da inserção das imagens de cada alimento -Api de busca personalizada Google-.
Java e JavaFx são responsáveis pela ligação entre o back-end e front-end.
Nele é possível: 
* Consultar os dados de centenas de alimentos (Principais nutrientes, foto e descriçao)
* Solicitar aos administradores do aplicativo a inserção de um alimento específico no sistema
* Criar uma refeição , selecionando os alimentos e a quantidade (em gramas) de cada um
* Vizualizar a lista de refeções criadas e as informações nutricionais de cada uma (Quantidade total de cada nutriente, gráficos e os alimentos presentes na refeição)
* Adicionar refeições criadas à dias da semana (Layout interativo de agenda semanal), oferecendo uma organização de rotina para o usuário
* Obter informações e dados de cada dia da semana (refeições presentes, nutrientes totais...)
* Obter a média semanal de consumo dos principais nutrientes por dia, gerando um feedback sobre a atual situação alimentar do usuário
* Gerar um relarório em PDF com todos os dados da rotina do usuário, funcionalidade bastante útil para avaliações físicas em academias, consultas médicas e até consultas com nutricionistas,
*  já que permite uma ótima noção do atual estado alimentar do usuário.
 Uma das principais motivações para a construção do projeto foi a ideia de proporcionar ao usuário uma noção concreta de sua rotina de alimentação, com dados e poder de gerenciamento individual, 
 funcionalidades essas que raramente são acatadas em conjunto nos aplicativos/sites e que permitem uma gama de formas de utilização.
Para fins de exemplo, o usuário pode iniciar replicando suas alimentaçoes díarias e atribuindo na agenda semanal da forma com que pratica na vida real, obtendo assim os principais dados nutricionais de sua rotina atual e ir adaptando gradativamente (os alimentos e refeiçoes) de acordo com o seu objetivo de dieta.
